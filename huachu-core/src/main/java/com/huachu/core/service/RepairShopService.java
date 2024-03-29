package com.huachu.core.service;

import com.github.pagehelper.Page;
import com.huachu.common.exception.BOException;
import com.huachu.common.util.BeanUtil;
import com.huachu.common.web.ApiResultCode;
import com.huachu.core.dao.TRepairShopMapper;
import com.huachu.domain.TRepairShop;
import com.huachu.dto.request.RepairShopReqDTO;
import com.huachu.dto.request.query.QueryRepairShopReqDTO;
import com.huachu.dto.response.RepairShopRespDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author zephyr
 * @date 2018/12/31
 */
@Service
public class RepairShopService {

    @Autowired
    TRepairShopMapper repairShopMapper;

    public Page<RepairShopRespDTO> queryList(QueryRepairShopReqDTO reqDto) {
        return repairShopMapper.queryList(reqDto);
    }

    @Transactional(rollbackFor = Exception.class)
    public Boolean create(RepairShopReqDTO reqDto) {
        TRepairShop repairShop = repairShopMapper.selectByRepairShopName(reqDto.getRepairShopName(), 0);
        if (repairShop != null) {
            throw new BOException(ApiResultCode.EXIST_DATA, "修理厂名称已存在");
        }
        TRepairShop record = BeanUtil.copy(reqDto, TRepairShop.class);
        int addRow = repairShopMapper.insertSelective(record);
        if (addRow < 1) {
            throw new BOException(ApiResultCode.SERVICE_ERROR);
        }
        return Boolean.TRUE;
    }

    @Transactional(rollbackFor = Exception.class)
    public Boolean modify(RepairShopReqDTO reqDto) {
        TRepairShop oldRecord = repairShopMapper.selectByPrimaryKey(reqDto.getId());
        if (oldRecord == null) {
            throw new BOException(ApiResultCode.NO_DATA, "修理厂信息不存在");
        }
        TRepairShop repairShop = repairShopMapper.selectByRepairShopName(reqDto.getRepairShopName(), reqDto.getId());
        if (repairShop != null) {
            throw new BOException(ApiResultCode.EXIST_DATA, "修理厂名称已存在");
        }
        TRepairShop record = BeanUtil.copy(reqDto, TRepairShop.class);
        int updateRow = repairShopMapper.updateByPrimaryKeySelective(record);
        if (updateRow < 1) {
            throw new BOException(ApiResultCode.SERVICE_ERROR);
        }
        return Boolean.TRUE;
    }

    @Transactional(rollbackFor = Exception.class)
    public Boolean delete(Integer id) {
        TRepairShop oldRecord = repairShopMapper.selectByPrimaryKey(id);
        if (oldRecord == null) {
            throw new BOException(ApiResultCode.NO_DATA, "修理厂信息不存在");
        }
        int deleteRow = repairShopMapper.deleteByPrimaryKey(id);
        if (deleteRow < 1) {
            throw new BOException(ApiResultCode.SERVICE_ERROR);
        }
        return Boolean.TRUE;
    }
}
