package com.huachu.core.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huachu.common.constants.ClientType;
import com.huachu.common.constants.Constant;
import com.huachu.common.constants.RedisConst;
import com.huachu.common.dto.AuthUserDTO;
import com.huachu.common.exception.BOException;
import com.huachu.common.service.RedisService;
import com.huachu.common.util.BeanUtil;
import com.huachu.common.util.CollectionsUtil;
import com.huachu.common.util.StringUtils;
import com.huachu.common.util.TokenUtil;
import com.huachu.common.web.ApiResultCode;
import com.huachu.core.dao.TAdminRoleMapper;
import com.huachu.core.dao.TAdminUserMapper;
import com.huachu.domain.TAdminMenu;
import com.huachu.domain.TAdminRole;
import com.huachu.domain.TAdminUser;
import com.huachu.dto.request.LoginReqDTO;
import com.huachu.dto.response.LoginRespDTO;

/**
 *@author zephyr
 *@date 2018-10-28
 */
@Service
public class LoginService {

    @Autowired
    TAdminUserMapper adminUserMapper;
    @Autowired
    RedisService redisService;
    @Autowired
    TAdminRoleMapper adminRoleMapper;
    @Autowired
    AdminMenuService adminMenuService;

    public LoginRespDTO login(LoginReqDTO reqDTO) {
    	String clientType = reqDTO.getClientType();
		ClientType ct = ClientType.getClientType(clientType);
		if (ct == null) {
			throw new BOException(ApiResultCode.ILLEGAL_ACCESS);
		}
        TAdminUser tUser = adminUserMapper.queryByUsername(reqDTO.getUsername());
        if(tUser == null) {
        	tUser = adminUserMapper.queryByPhone(reqDTO.getUsername());
        }
        if (tUser == null) {
            throw new BOException(ApiResultCode.NO_DATA, "用户不存在");
        }
        if (!tUser.getPassWord().equals(reqDTO.getPassword())) {
            throw new BOException(ApiResultCode.PWD_ERROR);
        }
        List<TAdminRole> roleList = adminRoleMapper.selectByUserId(tUser.getUserId());
        if (roleList == null || roleList.isEmpty()) {
        	throw new BOException(ApiResultCode.NO_ROLE);
        }
        AuthUserDTO authUser = BeanUtil.copy(tUser, AuthUserDTO.class);
        List<String> roleNoList = roleList.stream().map(TAdminRole::getRoleNo).collect(Collectors.toList());
        authUser.setRoleNos(roleNoList);
        redisService.set(RedisConst.LOGIN_USER_INFO+authUser.getUserId(), authUser);
        //TODO 获取用户授权菜单，接口访问权限缓存
        List<TAdminMenu> adminMenus = new ArrayList<>();
        if (roleNoList.contains(Constant.ROLE_ADMIN)) {
        	adminMenus = adminMenuService.listAllMenu();
        } else {
        	adminMenus = adminMenuService.listAuthMenu(authUser.getUserId());
        }
        // 将菜单的授权码缓存到redis
        List<String> permitIdentifiers = setUserOperAuthorized(adminMenus, authUser.getUserId());
        LoginRespDTO respDTO = new LoginRespDTO();
        respDTO.setNickname(authUser.getRealName());
        respDTO.setAccessToken(TokenUtil.createAccessToken(authUser.getUserId(), ct));
        respDTO.setPermitIdentifiers(permitIdentifiers);
        TAdminUser adminUser = null;
        try {
        	adminUser = new TAdminUser();
        	adminUser.setUserId(tUser.getUserId());
        	adminUser.setLastLoginTime(new Date());
        	adminUserMapper.updateByPrimaryKeySelective(adminUser);
        } catch(Exception e) {
        	e.printStackTrace();
        }
        return respDTO;
    }
    
    /**
     * 缓存用户的操作权限
     * @param sysMenus
     * @param userId
     */
    public List<String> setUserOperAuthorized(List<TAdminMenu> menus, Integer userId) {
        List<String> permitIdentifierList = new ArrayList<>();
        String key_prefix = RedisConst.USER_OPER_AUTH + userId + RedisConst.KEY_SPLITER;
        String key = null;
        Collection<Object> keys = redisService.keys(key_prefix + "*");
        redisService.delete(keys);
        if(menus != null && menus.size() > 0) {
            for(TAdminMenu menu : menus) {
                if(StringUtils.isNotEmpty(menu.getPermitIdentifier())) {
                    key = key_prefix + menu.getPermitIdentifier();
                    redisService.set(key, menu.getPermitIdentifier());
                }
                permitIdentifierList.add(menu.getPermitIdentifier());
            }
        }
        return CollectionsUtil.removeDuplicate(permitIdentifierList);
    }
}
