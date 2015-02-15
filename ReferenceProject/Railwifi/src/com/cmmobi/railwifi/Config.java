package com.cmmobi.railwifi;


public final class Config {

	/**
	 * 测试环境
	 * */
//	public static final String SERVER_CRM_URL    = "http://125.39.224.101:18000/download/";    //升级，内网测试地址
//	public static final String SERVER_CRM_URL    = "http://192.168.100.113:8899/download";    //升级，测试地址
	public static final String SERVER_CRM_URL     = "http://channel.looklook.cn:8091/download"; //升级及推荐, 生产地址
	
	/*
	 *  生产环境，勿启用
	 **/
//	public static final String SERVER_RIA_URL    = "http://ria.iluokuang.cn:8888"; 
//	public static final String SERVER_DC_URL    = "http://dc.iluokuang.cn:8888"; 
	public static final String SERVER_RIA_URL    = "http://testria.iluokuang.cn:8888"; 
	public static final String SERVER_DC_URL    = "http://testdc.iluokuang.cn:8888"; 

//	public static final String SERVER_CRM_URL     = "http://channel.looklook.cn:8091/download"; //升级及推荐, 生产地址

	//是否使用深圳提供的播放器
	public static final Boolean IS_USE_COMMOBI_VIDEOVIEW = true;
	
	//支付宝支付时是否使用实际价格
	public static final boolean IS_USE_REAL_PRICE = true;
	
	//是否使用搜狐视频
	public static final boolean IS_USE_SOHU_MOVIE = false;
	
	//是否使用StrictMode
	public static final boolean IS_USER_STRICTMODE = false;
	
	private Config() {
	}
}
