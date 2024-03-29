package com.huachu.core.manager;

import com.huachu.common.exception.BOException;
import com.huachu.common.web.ApiResultCode;
import com.huachu.core.dao.TRecyclingAccessoryMapper;
import com.huachu.core.dao.TRecyclingDispatchMapper;
import com.huachu.core.dao.TRecyclingListMapper;
import com.huachu.domain.TRecyclingAccessory;
import com.huachu.domain.TRecyclingDispatch;
import com.huachu.domain.TRecyclingList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class RecyclingListManager {

	private static final Logger logger = LoggerFactory.getLogger(RecyclingListManager.class);
	@Autowired
	TRecyclingListMapper recyclingListMapper;
	@Autowired
	TRecyclingDispatchMapper recyclingDispatchMapper;
	@Autowired
	TRecyclingAccessoryMapper recyclingAccessoryMapper;
	
	@Transactional(rollbackFor = Exception.class)
	public Boolean dispatchRecyclingList(List<TRecyclingDispatch> dispatchRecyclingList) {
		int count = 0;
		for (TRecyclingDispatch record : dispatchRecyclingList) {
			// 1、更新配件的派单信息
			recyclingAccessoryMapper.updateDispatchRecyclingAccessory(record.getRecyclingUserId(), record.getRecyclingNo());
			// 2、生成派单记录
			count = recyclingDispatchMapper.insertSelective(record);
			if (count < 1) {
				throw new BOException(ApiResultCode.CUSTOMIZE_ERROR, "派单失败，生成派单记录失败");
			}
			// 3、更新回收单的派单记录ID
			count = recyclingListMapper.updateDispatchRecyclingList(record.getId(), record.getRecyclingNo());
			if (count < 1) {
				throw new BOException(ApiResultCode.CUSTOMIZE_ERROR, "派单失败，回收单已全部回收完成");
			}
			
		}
		return Boolean.TRUE;
	}

	@Transactional(rollbackFor = Exception.class)
	public int createRecyclingList(TRecyclingList recyclingList, TRecyclingAccessory recyclingAccessory) {
		int count = 0;
		if (recyclingList != null) {
			count = recyclingListMapper.insertSelective(recyclingList);
		}
		if (recyclingAccessory != null) {
			count = recyclingAccessoryMapper.insertSelective(recyclingAccessory);
		}
		logger.info("成功数：{}", count);
		return count;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public Boolean addRecyclingList(TRecyclingList recyclingList, List<TRecyclingAccessory> accessoryList) {
		int count = recyclingListMapper.insertSelective(recyclingList);
		if (count < 1) {
			throw new BOException(ApiResultCode.SERVICE_ERROR, "新增回收单失败");
		}
		count = recyclingAccessoryMapper.insertBatch(accessoryList);
		if (count < 1) {
			throw new BOException(ApiResultCode.SERVICE_ERROR, "新增配件失败");
		}
		return Boolean.TRUE;
	}
}
