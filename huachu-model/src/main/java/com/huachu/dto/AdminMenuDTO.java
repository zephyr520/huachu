package com.huachu.dto;

import java.util.List;

public class AdminMenuDTO {

	private Integer menuId;

    private String menuName;

    private String menuUrl;

    private String menuIcon;
    
    private List<AdminMenuDTO> subMenus;

	public Integer getMenuId() {
		return menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	public String getMenuIcon() {
		return menuIcon;
	}

	public void setMenuIcon(String menuIcon) {
		this.menuIcon = menuIcon;
	}

	public List<AdminMenuDTO> getSubMenus() {
		return subMenus;
	}

	public void setSubMenus(List<AdminMenuDTO> subMenus) {
		this.subMenus = subMenus;
	}
}
