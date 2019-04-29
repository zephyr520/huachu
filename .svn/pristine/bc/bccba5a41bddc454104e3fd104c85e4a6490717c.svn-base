package com.huachu.core.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.huachu.common.exception.BOException;
import com.huachu.common.web.ApiResultCode;
import com.huachu.core.dao.TAdminRoleMapper;
import com.huachu.core.dao.TAdminUserMapper;
import com.huachu.core.dao.TAdminUserRoleMapper;
import com.huachu.domain.TAdminUser;
import com.huachu.domain.TAdminUserRole;

@Component
public class AdminUserManager {

	@Autowired
	TAdminUserMapper adminUserMapper;
	@Autowired
	TAdminRoleMapper adminRoleMapper;
	@Autowired
	TAdminUserRoleMapper adminUserRoleMapper;
	
	@Transactional(rollbackFor = Exception.class)
	public Boolean createUser(TAdminUser record, TAdminUserRole userRole) {
		int addRow = adminUserMapper.insertSelective(record);
        if (addRow < 1) {
            throw new BOException(ApiResultCode.SERVICE_ERROR, "新增用户信息失败");
        }
        userRole.setUserId(record.getUserId());
        addRow = adminUserRoleMapper.insertSelective(userRole);
		if (addRow < 1) {
			throw new BOException(ApiResultCode.SERVICE_ERROR, "分配用户角色失败");
		}
		return Boolean.TRUE;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public Boolean modifyUser(TAdminUser record, TAdminUserRole userRole) {
		int updateRow = adminUserMapper.updateByPrimaryKeySelective(record);
        if (updateRow < 1) {
            throw new BOException(ApiResultCode.SERVICE_ERROR, "修改用户信息失败");
        }
        adminUserRoleMapper.deleteByUserId(userRole.getUserId());
		int addRow = adminUserRoleMapper.insertSelective(userRole);
		if (addRow < 1) {
			throw new BOException(ApiResultCode.SERVICE_ERROR, "用户分配角色失败");
		}
		return Boolean.TRUE;
	}
}
