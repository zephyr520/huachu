package com.huachu.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.huachu.common.exception.BOException;
import com.huachu.common.util.BeanUtil;
import com.huachu.common.web.ApiResultCode;
import com.huachu.core.dao.TStaffMapper;
import com.huachu.domain.TStaff;
import com.huachu.dto.request.StaffReqDTO;
import com.huachu.dto.request.query.QueryStaffReqDTO;
import com.huachu.dto.response.StaffRespDTO;

/**
 *
 * @author zephyr
 * @date 2018/12/31
 */
@Service
public class StaffService {

    @Autowired
    TStaffMapper staffMapper;

    public Page<StaffRespDTO> queryList(QueryStaffReqDTO reqDto) {
        return staffMapper.queryList(reqDto);
    }
    
    @Transactional(rollbackFor = Exception.class)
    public Boolean create(StaffReqDTO reqDto) {
    	TStaff staff = staffMapper.selectByUniqueKey(reqDto.getStaffPhone(), 0);
    	if (staff != null) {
    		throw new BOException(ApiResultCode.EXIST_DATA, "手机号重复");
    	}
    	TStaff record = BeanUtil.copy(reqDto, TStaff.class);
    	int addRow = staffMapper.insertSelective(record);
    	if (addRow < 1) {
    		throw new BOException(ApiResultCode.SERVICE_ERROR);
    	}
    	return Boolean.TRUE;
    }
    
    @Transactional(rollbackFor = Exception.class)
    public Boolean modify(StaffReqDTO reqDto) {
    	TStaff oldRecord = staffMapper.selectByPrimaryKey(reqDto.getId());
    	if (oldRecord == null) {
    		throw new BOException(ApiResultCode.NO_DATA, "定损员信息不存在");
    	}
    	TStaff staff = staffMapper.selectByUniqueKey(reqDto.getStaffPhone(), reqDto.getId());
    	if (staff != null) {
    		throw new BOException(ApiResultCode.EXIST_DATA, "定损员手机号重复");
    	}
    	TStaff record = BeanUtil.copy(reqDto, TStaff.class);
    	int updateRow = staffMapper.updateByPrimaryKeySelective(record);
    	if (updateRow < 1) {
    		throw new BOException(ApiResultCode.SERVICE_ERROR);
    	}
    	return Boolean.TRUE;
    }
}
