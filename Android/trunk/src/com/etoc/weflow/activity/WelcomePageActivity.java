package com.etoc.weflow.activity;


import java.util.List;

import com.etoc.weflow.R;
import com.etoc.weflow.WeFlowApplication;
import com.etoc.weflow.activity.login.GuideActivity;
import com.etoc.weflow.activity.login.LoginActivity;
import com.etoc.weflow.dao.AccountInfo;
import com.etoc.weflow.dao.AccountInfoDao;
import com.etoc.weflow.dao.DaoMaster;
import com.etoc.weflow.dao.DaoMaster.DevOpenHelper;
import com.etoc.weflow.dao.DaoSession;
import com.etoc.weflow.dialog.PromptDialog;
import com.etoc.weflow.download.DownloadManager;
import com.etoc.weflow.download.DownloadType;
import com.etoc.weflow.net.GsonResponseObject.*;
import com.etoc.weflow.net.Requester;
import com.etoc.weflow.utils.VMobileInfo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Message;
import android.os.Parcelable;
import android.util.Log;
import android.widget.TextView;

public class WelcomePageActivity extends TitleRootActivity {

	private DaoMaster daoMaster;
	private DaoSession daoSession;
	private SQLiteDatabase db;
	private AccountInfoDao accountInfoDao;
	
	private List<AccountInfo> aiList = null;
	
	private TextView tvVersion;
	
	private static final int INTV_TIME = 3 * 1000;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
//		AdManager.getInstance(this).init("81b5d6a3b8130593", "51f70349743c649d", false);
		if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {  
            //结束你的activity
			this.finish();
            return;  
        }
		hideTitlebar();
		
		overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
		SharedPreferences sp=getSharedPreferences(GuideActivity.SP_NAME, MODE_PRIVATE);
		if(sp.getInt(GuideActivity.SP_KEY, 0) == 0){
			Intent intent = new Intent(this, GuideActivity.class);
			startActivity(intent);
			this.finish();
			return;
		}
		
		initView();
		
		SharedPreferences shortcutpref = WeFlowApplication.getAppInstance().getSharedPreferences("shortcut", Context.MODE_PRIVATE);
		boolean iscreated = shortcutpref.getBoolean("iscreated", false);
		if (!iscreated) {
			createDeskShortCut();
		}
		
		DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "weflowdb", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        
        accountInfoDao = daoSession.getAccountInfoDao();
        aiList = accountInfoDao.loadAll();
        
        if(aiList != null && aiList.size() > 0) {
        	AccountInfo lastAcc = aiList.get(0);
        	if(lastAcc.getUserid() != null && !lastAcc.getUserid().equals("")) {
        		//存在userid
        		Requester.autoLogin(false, handler, lastAcc.getUserid());
        		return;
        	}
        }
		handler.postDelayed(runnable, INTV_TIME);
	}
	
	
	
	private void initView() {
		// TODO Auto-generated method stub
		tvVersion = (TextView) findViewById(R.id.tv_version);
		String ver = VMobileInfo.getAppVersionName(this);
		tvVersion.setText("v" + ver);
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(db != null) db.close();
	}

	Runnable runnable = new Runnable() {
		
		@Override
		public void run() {
			toMainActivity();
		}
	};
	
	private void toMainActivity() {
		Intent intent = new Intent();
		intent.setClass(this, MainActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
		overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
		this.finish();
	}

	@Override
	public boolean handleMessage(Message msg) {
		switch(msg.what) {
		case Requester.RESPONSE_TYPE_AUTO_LOGIN:
			autoLoginResponse alogin = (autoLoginResponse) msg.obj;
			if(alogin != null) {
				if(alogin.status.equals("0") || alogin.status.equals("0000")) { //自动登录成功
					AccountInfo acc = new AccountInfo();
					acc.setIsregistration(alogin.isregistration);
					acc.setFlowcoins(alogin.flowcoins);
					acc.setMakeflow(alogin.makeflow);
					acc.setUseflow(alogin.useflow);
					acc.setUserid(alogin.userid);
					acc.setTel(alogin.tel);
					accountInfoDao.deleteAll();
					accountInfoDao.insertOrReplace(acc);
					if(alogin.tel != null && !alogin.tel.equals("")) {
						WeFlowApplication.getAppInstance().addJPushTag(alogin.tel);
					}
					handler.postDelayed(runnable, INTV_TIME);
				} else {
					PromptDialog.Alert(WelcomePageActivity.class, "登录失败,请重新登录");
					Intent i = new Intent(this, LoginActivity.class);
					if(aiList != null && aiList.size() > 0) {
						i.putExtra("tel", aiList.get(0).getTel());
					}
					startActivity(i);
					this.finish();
				}
			} else {
				PromptDialog.Alert(WelcomePageActivity.class, "您的网络不给力啊！");
				handler.postDelayed(runnable, INTV_TIME);
			}
			break;
/*		case Requester.RESPONSE_TYPE_UPDATE:
			if (msg.obj != null) {
				final UpdateResp resp = (UpdateResp) msg.obj;
				if (Requester.isSuccessed(resp.status)) {
					try {
						resp.type = "2";
						//已经最新
						if ("0".equals(resp.type)) {
//							PromptDialog.Dialog(this, "版本升级", "当前已经是最新版本", "确定");
							//普通升级
						} else if ("1".equals(resp.type)) {
							PromptDialog.Dialog(this, true, true, false, "版本升级", resp.description, "下载", "取消", new OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									// TODO Auto-generated method stub
									DownloadManager.getInstance().addDownloadTask(resp.filepath, "0", resp.version, "", "",  DownloadType.APP, "", "","","com.etoc.weflow");
								}
							}, null, false, null);
							//强制升级
						} else if ("2".equals(resp.type)){
							PromptDialog.Dialog(this, false, false, false, "版本升级", resp.description, "下载", "取消", new OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									// TODO Auto-generated method stub
									DownloadManager.getInstance().addDownloadTask(resp.filepath, "0", resp.version, "", "",  DownloadType.APP, "", "","","com.etoc.weflow");
								}
							}, null, false, null);
						}
					} catch(Exception e) {
						e.printStackTrace();
					}
				}
			}
			break;*/
		}
		return false;
	}

	@Override
	public int subContentViewId() {
		// TODO Auto-generated method stub
		return R.layout.activity_welcome;
	}

	/**
	 * 创建快捷方式
	 */
	public void createDeskShortCut() {
		// 创建快捷方式的Intent
		Intent shortcutIntent = new Intent(
				"com.android.launcher.action.INSTALL_SHORTCUT");
		// 不允许重复创建
		shortcutIntent.putExtra("duplicate", false);
		// 需要显示的名称
		shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME,
				getString(R.string.app_name));

		// 快捷图片
		Parcelable icon = Intent.ShortcutIconResource.fromContext(
				getApplicationContext(), R.drawable.ic_launcher);

		shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);

		Intent intent = new Intent(getApplicationContext(),
				WelcomePageActivity.class);
		// 下面两个属性是为了当应用程序卸载时桌面 上的快捷方式会删除
		intent.setAction("android.intent.action.MAIN");
		intent.addCategory("android.intent.category.LAUNCHER");
		// 点击快捷图片，运行的程序主入口
		shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent);
		// 发送广播。OK
		sendBroadcast(shortcutIntent);

		// 在配置文件中声明已经创建了快捷方式
		getSharedPreferences("shortcut", Context.MODE_PRIVATE).edit()
				.putBoolean("iscreated", true).commit();

		// 2.3.3系统创建快捷方式不提示
		/*
		 * if (android.os.Build.VERSION.SDK.equals("10")) { Toast.makeText(
		 * this, "已创建" + this.getResources().getString(R.string.app_name) +
		 * "快捷方式。", Toast.LENGTH_LONG).show(); }
		 */

		String handSetInfo = "手机型号:" + android.os.Build.MODEL + ",SDK版本:"
				+ android.os.Build.VERSION.SDK + ",系统版本:"
				+ android.os.Build.VERSION.RELEASE;
		Log.e("HANDINFO", handSetInfo);
	}
	
}
