package com.huachu.core.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huachu.common.constants.Constant;
import com.huachu.common.dto.AuthUserDTO;
import com.huachu.common.util.BeanUtil;
import com.huachu.core.dao.TAdminMenuMapper;
import com.huachu.core.dao.TAdminRoleMenuMapper;
import com.huachu.domain.TAdminMenu;
import com.huachu.dto.AdminMenuDTO;

@Service
public class AdminMenuService {

	@Autowired
	TAdminMenuMapper adminMenuMapper;
	@Autowired
	TAdminRoleMenuMapper adminRoleMenuMapper;
	
	/**
	 * 获取所有菜单列表
	 * @return
	 */
	public List<TAdminMenu> listAllMenu() {
		List<TAdminMenu> menus = adminMenuMapper.queryAllMenus();
		if (menus == null || menus.isEmpty()) {
			menus = new ArrayList<>();
		}
		return menus;
	}
	
	/***
	 * 获取用户已授权的菜单
	 * @param userId
	 * @return
	 */
	public List<TAdminMenu> listAuthMenu(Integer userId) {
		List<TAdminMenu> menus = adminMenuMapper.listAuthMenu(userId);
		if (menus == null || menus.isEmpty()) {
			menus = new ArrayList<>();
		}
		return menus;
	}
	
	/**
	 *  获取用户授权菜单树
	 * @param userId
	 * @return
	 */
	public List<AdminMenuDTO> listTreeMenu(AuthUserDTO authUser) {
		List<TAdminMenu> menus = null;
		if (authUser.getRoleNos().contains(Constant.ROLE_ADMIN)) {
			menus = listAllMenu();
		} else {
			menus = listAuthMenu(authUser.getUserId());
		}
		menus.sort(Comparator.comparing(TAdminMenu::getType).thenComparing(TAdminMenu::getOrderNum));
		return treeMenuList(menus, 0);
	}
	
	private List<AdminMenuDTO> treeMenuList(List<TAdminMenu> menus, Integer parentId) {
        List<AdminMenuDTO> menuList = new ArrayList<>();
        for (TAdminMenu menu : menus) {
    		AdminMenuDTO menuDTO = BeanUtil.copy(menu, AdminMenuDTO.class);
            if (menu.getParentId().compareTo(parentId) == 0) {
                if (menu.getType().compareTo(Constant.MENU_BTN.byteValue()) != 0) {
                    List<AdminMenuDTO> treeList = treeMenuList(menus, menuDTO.getMenuId());
                    menuDTO.setSubMenus(treeList);
                    menuList.add(menuDTO);
                }
            }
        }
        return menuList;
    }
	
	public List<Integer> queryAuthMenuIdsByRoleId(Integer roleId) {
		return adminRoleMenuMapper.queryMenuIdsByRoleId(roleId);
	}
}
