package com.cmmobi.railwifi.network;

public class CRM_Object {
	
	//3. 移动终端软件获取推荐应用列表
	public static class getRecommendRequest {
		public String systemcode;
		public String productcode;
	}
	
	public static class getRecommendResponse {
		public String total;
		public recItem[] items;
	}
	
	public static class recItem {
		public String appimage;//推荐应用图标地址 例如：http://v.looklook.cn:8091/download/recommendFile/icon/a21c7d43-1a71-4a18-83f1-bdf81801f04c/101/qq.jpg
		public String appname;//推荐应用名称 例如：QQ
		public String appdes;//推荐应用描述 例如：123
		public String loadpath;//推荐应用地址 分为两种情况 
		//1-	推荐应用为本地文件，例如：http://v.looklook.cn:8091/download/recommendFile/install/a21c7d43-1a71-4a18-83f1-bdf81801f04c/101/qq.apk
		//	2-	推荐应用为外部链接 例如：
		//	http://www.163.com/
		public String islocal;//推荐应用是否为本地文件  1  本地文件  0  外部链接
		public String recommendcode;//推荐应用信息ID，推荐应用信息在客户端升级下载平台进行维护，该参数用于统计下载量 例如：83
		public String ismain;//是否主推 例如1—主推  0—不主推
		public String appbigimage;//主推荐应用大图标地址
		public String downloadcount;//下载次数 例如 123
		public String appkind;//推荐应用属于哪个种类 例如 教育
		public String appsort;//主推顺序 例如 1
		public String total;//推荐应用条目数，例如：1
	}
	
	public static class versionCheckRequest{
		public String version; //例如：1_0_0
		public String system; //移动终端软件属于哪个系统，系统信息在客户端升级下载平台进行维护，该参数为系统信息ID，安卓的ID是101，苹果是161
		public String productcode; //:"3",
		public String imei; //:"0_9_0",
		public String channelcode ;//例如：channel0190
	}
	
	public static class versionCheckResponse {
		public String type; //"2",
		public String path; //:"http://192.168.100.114:8080/vs/",
		public String description; //:"测试",
		public String versionnumber; //:"0_9_0",
		public String filesize ;//:"0",
		public String servertime; //:"1346742176498"}
	}
	
}
