package com.huachu.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.google.common.collect.ImmutableList;
import com.huachu.common.constants.Constant;
import com.huachu.common.constants.RedisConst;
import com.huachu.common.dto.AuthUserDTO;
import com.huachu.common.exception.BOException;
import com.huachu.common.service.RedisService;
import com.huachu.common.util.BeanUtil;
import com.huachu.common.web.ApiResultCode;
import com.huachu.core.dao.TAdminRoleMapper;
import com.huachu.core.dao.TAdminUserMapper;
import com.huachu.core.dao.TAdminUserRoleMapper;
import com.huachu.core.manager.AdminUserManager;
import com.huachu.domain.TAdminRole;
import com.huachu.domain.TAdminUser;
import com.huachu.domain.TAdminUserRole;
import com.huachu.dto.request.AdminUserReqDTO;
import com.huachu.dto.request.query.QueryAdminUserReqDTO;
import com.huachu.dto.response.AdminUserRespDTO;
import com.huachu.dto.response.RecyclingUserRespDTO;

@Service
public class AdminUserService {

	@Autowired
	RedisService redisService;
	@Autowired
	TAdminUserMapper adminUserMapper;
	@Autowired
	TAdminRoleMapper adminRoleMapper;
	@Autowired
	TAdminUserRoleMapper adminUserRoleMapper;
	@Autowired
	AdminUserManager adminUserManager;
	
	public Page<AdminUserRespDTO> queryList(QueryAdminUserReqDTO reqDto) {
		return adminUserMapper.queryListByPage(reqDto);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public Boolean createUser(AdminUserReqDTO reqDto) {
		Boolean result = Boolean.FALSE;
		TAdminUser record = BeanUtil.copy(reqDto, TAdminUser.class);
		
        TAdminUserRole userRole = new TAdminUserRole();
		userRole.setRoleId(reqDto.getRoleId());
		try {
			result = adminUserManager.createUser(record, userRole);
			if (result) {
				refreshUserCache(userRole.getUserId(), userRole.getRoleId());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BOException(ApiResultCode.SERVICE_ERROR, "创建用户信息失败");
		}
		
		return result;
	}
	
	private void refreshUserCache(Integer userId, Integer roleId) {
		AuthUserDTO authUser = redisService.get(RedisConst.LOGIN_USER_INFO + userId, AuthUserDTO.class);
		if (authUser != null) {
			TAdminRole role = adminRoleMapper.selectByPrimaryKey(roleId);
			if (role != null) {
				authUser.setRoleNos(ImmutableList.of(role.getRoleNo()));
				redisService.set(RedisConst.LOGIN_USER_INFO + userId, authUser);
			}
		}
	}

	public Boolean authRole(Integer userId, Integer roleId) {
		TAdminUserRole userRole = new TAdminUserRole();
		userRole.setUserId(userId);
		userRole.setRoleId(roleId);
		adminUserRoleMapper.deleteByPrimaryKey(userRole.getUserId(), userRole.getRoleId());
		int addRow = adminUserRoleMapper.insertSelective(userRole);
		if (addRow < 1) {
			throw new BOException(ApiResultCode.SERVICE_ERROR, "用户分配角色失败");
		}
		return Boolean.TRUE;
	}
	
	public Boolean modifyUser(AdminUserReqDTO reqDto) {
		Boolean result = Boolean.FALSE;
		TAdminUser oldRecord = adminUserMapper.selectByPrimaryKey(reqDto.getUserId());
		if (oldRecord == null) {
			throw new BOException(ApiResultCode.NO_DATA, "用户信息不存在");
		}
		TAdminUser record = BeanUtil.copy(reqDto, TAdminUser.class);
		
		TAdminUserRole userRole = new TAdminUserRole();
		userRole.setUserId(reqDto.getUserId());
		userRole.setRoleId(reqDto.getRoleId());
		
		try {
			result = adminUserManager.modifyUser(record, userRole);
			if (result) {
				refreshUserCache(userRole.getUserId(), userRole.getRoleId());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BOException(ApiResultCode.SERVICE_ERROR, "创建用户信息失败");
		}
		
		return result;
	}

	@Transactional(rollbackFor = Exception.class)
	public Boolean deleteUser(Integer userId) {
		TAdminUser oldRecord = adminUserMapper.selectByPrimaryKey(userId);
		if (oldRecord == null) {
			throw new BOException(ApiResultCode.NO_DATA, "用户不存在");
		}
		if (!oldRecord.getIfOperate()) {
			throw new BOException(ApiResultCode.DELETE_FORBID);
		}
		TAdminUser record = new TAdminUser();
		record.setUserId(userId);
		record.setStatus(Constant.DISABLE);
		int deleteRow = adminUserMapper.updateByPrimaryKeySelective(record);
		if (deleteRow < 1) {
			throw new BOException(ApiResultCode.SERVICE_ERROR);
		}
		return Boolean.TRUE;
	}
	
	public List<RecyclingUserRespDTO> queryRecyclingUserList(String roleNo) {
		return adminUserMapper.queryRecyclingUserList(roleNo);
	}
}
