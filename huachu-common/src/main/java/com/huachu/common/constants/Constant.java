package com.huachu.common.constants;

/**
 * @author Administrator
 */
public class Constant {


	public static final String ACCESS_TOKEN = "token";
	public static final String USER_CONTEXT = "USER_CONTEXT";
	public static final String USER_CLIENT = "USER_CLIENT";
	/**
	 * ADMIN后台系统token的过期时间 (2小时过期)
	 */
	public static final Long ACCESS_TOKEN_EXPIRED_SECONDS = 2 * 60 * 60 * 1000L;
	/**
	 *  ANDROID端token的过期时间(30天过期)
	 */
	public static final Long ACCESS_TOKEN_APP_EXPIRED_SECONDS = 30 * 24 * 60 * 60 * 1000L;

	/**
	 * 超级管理员,制单员，回收员，仓库管理员，销售员
	 */
	public static final String ROLE_ADMIN = "admin";
	public static final String ROLE_CREATOR = "creator";
	public static final String ROLE_RECYCLING = "recycling";
	public static final String ROLE_STOCKEDMAN = "stockman";
	public static final String ROLE_SALE_MAN = "saleman";


	/**
	 * 性别：1-男，2-女
	 */
	public static final Byte MALE = 1;
	public static final Byte FEMALE = 2;
	
	/**
	 *  用户状态：enable-正常，disable-禁用 
	 */
	public static final String ENABLE = "enable";
	public static final String DISABLE = "disable";
	

	/**
	 * 菜单类型：0-目录，1-菜单，2-按钮
	 */
	public static final Short MENU_CATALOG = 0;
	public static final Short MENU_LINK = 1;
	public static final Short MENU_BTN = 2;


}
