package com.huachu.core.service;

import com.github.pagehelper.Page;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.huachu.common.constants.Constant;
import com.huachu.common.constants.RedisConst;
import com.huachu.common.dto.AuthUserDTO;
import com.huachu.common.excel.ParserExcel;
import com.huachu.common.exception.BOException;
import com.huachu.common.service.RedisIdService;
import com.huachu.common.service.RedisService;
import com.huachu.common.util.BeanUtil;
import com.huachu.common.util.StringUtils;
import com.huachu.common.web.ApiResultCode;
import com.huachu.core.dao.TRecyclingAccessoryMapper;
import com.huachu.core.dao.TRecyclingListMapper;
import com.huachu.core.dao.TRepairShopMapper;
import com.huachu.core.manager.RecyclingListManager;
import com.huachu.core.queue.RecyclingListTask;
import com.huachu.domain.TRecyclingAccessory;
import com.huachu.domain.TRecyclingDispatch;
import com.huachu.domain.TRecyclingList;
import com.huachu.domain.TRepairShop;
import com.huachu.dto.RecyclingListDTO;
import com.huachu.dto.request.RecyclingListDispatchReqDTO;
import com.huachu.dto.request.RecyclingListReqDTO;
import com.huachu.dto.request.query.QueryRecyclingListReqDTO;
import com.huachu.dto.response.RecyclingListRespDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

@Service
public class RecyclingListService {

	private static final Logger logger = LoggerFactory.getLogger(RecyclingListService.class);
	@Autowired
	TRecyclingListMapper recyclingListMapper;
	@Autowired
	RecyclingListManager recyclingListManager;
	@Autowired
	TRecyclingAccessoryMapper recyclingAccessoryMapper;
	@Autowired
	RedisIdService redisIdService;
	@Autowired
	TRepairShopMapper repairShopMapper;
	@Autowired
	RedisService redisService;
	
	public Page<RecyclingListRespDTO> queryList(QueryRecyclingListReqDTO reqDto, AuthUserDTO authUser) {
		if (!authUser.getRoleNos().contains(Constant.ROLE_ADMIN)) {
			reqDto.setRecyclingUserId(authUser.getUserId());
		}
		return recyclingListMapper.queryList(reqDto);
	}
	
	public Boolean dispatchRecyclingList(RecyclingListDispatchReqDTO reqDto, AuthUserDTO authUser) {
		List<TRecyclingList> recyclingList = recyclingListMapper.selectByPrimaryKeyList(reqDto.getRecyclingNoList());
		if (!recyclingList.isEmpty() && recyclingList.size() != reqDto.getRecyclingNoList().size()) {
			throw new BOException(ApiResultCode.PARAM_ERROR, "对已全部回收完成的回收单，不能重复派单");
		}
		List<TRecyclingDispatch> dispatchRecyclingList = new ArrayList<>();
		TRecyclingDispatch record = null;
		Date dispatchTime = new Date();
		for (String recyclingNo : reqDto.getRecyclingNoList()) {
			List<TRecyclingAccessory> accessoryList = recyclingAccessoryMapper.queryAccessoryListByRecyclingNo(recyclingNo);
			if (accessoryList == null || accessoryList.isEmpty()) {
				throw new BOException(ApiResultCode.PARAM_ERROR, "此回收单(单号：" + recyclingNo + ")无配件，无需派单");
			}
			record = BeanUtil.copy(reqDto, TRecyclingDispatch.class);
			record.setRecyclingNo(recyclingNo);
			record.setDispatchUserId(authUser.getUserId());
			record.setDispatchTime(dispatchTime);
			
			dispatchRecyclingList.add(record);
		}
		try {
			return recyclingListManager.dispatchRecyclingList(dispatchRecyclingList);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BOException(ApiResultCode.SERVICE_ERROR, "派单失败");
		}
	}

	public Boolean recyclingListImport(String fileName, MultipartFile file) {
		ParserExcel excel = null;
		ExecutorService executorService = null;
		List<RecyclingListDTO> recyclingListDtos = new ArrayList<>();
		RecyclingListDTO dto = null;
		Future<List<RecyclingListDTO>> futureTask = null;
		long start = System.currentTimeMillis();
		try {
			excel = new ParserExcel();
			excel.open(fileName, file.getInputStream());
			// 读取第一个Sheet表
			excel.setSheetNum(0);
			// 获取表的总行数
			int rowCount = excel.getRowCount() + 1;
			for (int i = 1; i < rowCount; i++) {
                if (excel.getRow(i) == null) {
                    continue;
                }
                String[] rows = excel.readExcelLine(i);
                dto = new RecyclingListDTO();
                // 三级机构
                dto.setOrganization(rows[0]);
                // 报案号
                dto.setFileNo(rows[2]);
                // 车牌号
                dto.setPlateNo(rows[3]);
                // 配件名称
                dto.setAccessoryName(rows[5]);
                // 是否是重点配件
                if (StringUtils.isNotEmpty(rows[6]) && "重点配件".equals(rows[6])) {
                    dto.setIfImportant(Boolean.TRUE);
                }
                // 配件价格
                String accessoryPrice = rows[7];
                if (StringUtils.isNotEmpty(accessoryPrice) && StringUtils.isNumericDigit(accessoryPrice)) {
                    dto.setAccessoryPrice(new BigDecimal(accessoryPrice));
                } else {
                    dto.setAccessoryPrice(BigDecimal.ZERO);
                }
                // 配件数量
                dto.setAccessoryNum(1);
                // 修理厂名称
                dto.setRepairShopName(rows[8]);
                // 车型
                dto.setCarModel(rows[11]);
                
                recyclingListDtos.add(dto);
			}
			int totalCount = recyclingListDtos.size();
			logger.info("读取到的总数据量：{}", totalCount);
			int len = totalCount / RecyclingListTask.CORE_POOL_SIZE;
			int size = totalCount % RecyclingListTask.CORE_POOL_SIZE == 0 ? len : len + 1 ;
			logger.info("excel的行数：{}，每个线程处理的数量：{}", totalCount, size);
			List<Future<List<RecyclingListDTO>>> futureTaskList = new ArrayList<>();
			executorService = new ThreadPoolExecutor(
					RecyclingListTask.CORE_POOL_SIZE, 50, 60,
					TimeUnit.SECONDS,
					new LinkedBlockingQueue<>(RecyclingListTask.QUEUE_MAX_SIZE),
					new ThreadFactoryBuilder().setNameFormat("recyclingList-queue-%d").build());
			for (int i = 0; i < RecyclingListTask.CORE_POOL_SIZE; i++) {
				futureTask = executorService.submit(new RecyclingListTask(this, recyclingListDtos, i * size, size * (i + 1) >= totalCount ? totalCount : size * (i + 1)));
				futureTaskList.add(futureTask);
			}
			for (Future<List<RecyclingListDTO>> f : futureTaskList) {
				List<RecyclingListDTO> list = f.get();
				if (list != null && !list.isEmpty()) {
					logger.info("回收单数量：{}", list.size());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			executorService.shutdown();
		}
		long end = System.currentTimeMillis();
		logger.info("导入数据总耗时：{}毫秒", (end - start));
		return Boolean.TRUE;
	}

	public void createRecyclingList(RecyclingListDTO recyclingListDTO) {
		TRecyclingAccessory recyclingAccessory = null;
		TRecyclingList recyclingList = null;
		boolean isDeleteKey = false;
		String redisKey = RedisConst.RECYCLING_LIST_KEY + recyclingListDTO.getFileNo() + RedisConst.KEY_SPLITER + recyclingListDTO.getPlateNo();
		String redisVal = recyclingListDTO.getFileNo() + RedisConst.KEY_SPLITER + recyclingListDTO.getPlateNo();
		try {
			Boolean uniqueKeyExist = redisService.setStringNX(redisKey, redisVal, RedisConst.RECYCLING_LIST_KEY_EXPIRED);
			// 缓存中不存在，则新增数据
			if (uniqueKeyExist) {
				TRecyclingList oldRecord = recyclingListMapper.selectByUniqueKey(recyclingListDTO.getFileNo(), recyclingListDTO.getPlateNo());
				if (oldRecord != null) {
					recyclingAccessory = BeanUtil.copy(recyclingListDTO, TRecyclingAccessory.class);
					recyclingAccessory.setRecyclingNo(oldRecord.getRecyclingNo());
				} else {
					TRepairShop repairShop = repairShopMapper.queryByUniqueKey(recyclingListDTO.getRepairShopName());
					if (repairShop == null) {
						repairShop = new TRepairShop();
						if (StringUtils.isNotBlank(recyclingListDTO.getRepairShopName())) {
							repairShop.setRepairShopName(recyclingListDTO.getRepairShopName());
							repairShopMapper.insertSelective(repairShop);
						}
					}
					String recyclingNo = redisIdService.genRecyclingNo();
					recyclingList = BeanUtil.copy(recyclingListDTO, TRecyclingList.class);
					recyclingList.setRepairShopId(repairShop.getId() == null ? 0 : repairShop.getId());
					recyclingList.setRecyclingNo(recyclingNo);
					
					recyclingAccessory = BeanUtil.copy(recyclingListDTO, TRecyclingAccessory.class);
					recyclingAccessory.setRecyclingNo(recyclingNo);
				}
			} else {
				TRecyclingList oldRecord = recyclingListMapper.selectByUniqueKey(recyclingListDTO.getFileNo(), recyclingListDTO.getPlateNo());
				if (oldRecord != null) {
					recyclingAccessory = BeanUtil.copy(recyclingListDTO, TRecyclingAccessory.class);
					recyclingAccessory.setRecyclingNo(oldRecord.getRecyclingNo());
				}
			}
			
			int count = recyclingListManager.createRecyclingList(recyclingList, recyclingAccessory);
			if (count > 0) {
				isDeleteKey = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (logger.isErrorEnabled()) {
				logger.error("回收单创建失败：{}", e.getMessage());
			}
		} finally {
			if (isDeleteKey) {
				redisService.removeLock(redisKey);
			}
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public Boolean deleteRecyclingList(String recyclingNo) {
		TRecyclingList oldRecord = recyclingListMapper.selectByPrimaryKey(recyclingNo);
		if (oldRecord == null) {
			throw new BOException(ApiResultCode.NO_DATA, "您要删除的回收单不存在");
		}
		List<TRecyclingAccessory> accessoryList = recyclingAccessoryMapper.queryAccessoryListByRecyclingNo(recyclingNo);
		if (accessoryList != null && !accessoryList.isEmpty()) {
			throw new BOException(ApiResultCode.EXIST_DATA, "你要删除的回收单存在配件,请先删除配件");
		}
		int deleteRow = recyclingListMapper.deleteByPrimaryKey(recyclingNo);
		if (deleteRow < 1) {
			throw new BOException(ApiResultCode.SERVICE_ERROR, "删除回收单操作失败");
		}
		return Boolean.TRUE;
	}
	
	public Boolean addRecyclingList(RecyclingListReqDTO reqDto, AuthUserDTO authUser) {
		TRecyclingList oldRecord = recyclingListMapper.selectByUniqueKey(reqDto.getFileNo(), reqDto.getPlateNo());
		if (oldRecord != null) {
			throw new BOException(ApiResultCode.NO_DATA, "您新增的回收单已存在");
		}
		String recyclingNo = redisIdService.genRecyclingNo();
		TRecyclingList recyclingList = BeanUtil.copy(reqDto, TRecyclingList.class);
		recyclingList.setRecyclingNo(recyclingNo);
		recyclingList.setCreateId(authUser.getUserId());
		
		List<TRecyclingAccessory> accessoryList = BeanUtil.copyTo(reqDto.getAccessoryList(), TRecyclingAccessory.class);
		accessoryList.stream().forEach(accessory -> accessory.setRecyclingNo(recyclingNo));
		
		try {
			return recyclingListManager.addRecyclingList(recyclingList, accessoryList);
		} catch(Exception e) {
			e.printStackTrace();
			throw new BOException(ApiResultCode.SERVICE_ERROR, "新增回收单操作异常");
		}
		
	}
}