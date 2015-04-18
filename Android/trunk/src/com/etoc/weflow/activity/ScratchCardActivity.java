package com.etoc.weflow.activity;

import java.util.ArrayList;
import java.util.HashMap;

import com.etoc.weflow.R;
import com.etoc.weflow.WeFlowApplication;
import com.etoc.weflow.dao.AccountInfo;
import com.etoc.weflow.dialog.OrderDialog;
import com.etoc.weflow.dialog.PromptDialog;
import com.etoc.weflow.net.GsonRequestObject.getScratchConfigRequest;
import com.etoc.weflow.net.GsonResponseObject.scratchConfigResp;
import com.etoc.weflow.net.GsonResponseObject.scratchflowResp;
import com.etoc.weflow.net.Requester;
import com.etoc.weflow.utils.DisplayUtil;
import com.etoc.weflow.utils.RandomUtils;
import com.etoc.weflow.utils.ViewUtils;
import com.etoc.weflow.view.ScratchTextView;
import com.etoc.weflow.view.ScratchTextView.OnCompletedListener;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ScratchCardActivity extends TitleRootActivity {

	private RelativeLayout rlFlowCost;
	private ImageView ivCover;
	private TextView tvFlow, tvFlowCost;
	private Button btnStartLottery;
	private ScratchTextView stvCard;
	private GridView gvAward;
	private String Tel  = "n/a";
	private String Flow = "n/a";
	
	private boolean isRetry = false;
	
	private AccountInfo accountInfo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initViews();
	}
	
	public void initViews() {
		Log.e("ScratchCardActivity", "initViews IN!");
		setLeftButtonBackground(R.drawable.btn_back);
		hideRightButton();
		setTitleText("刮刮卡");
		
		accountInfo = WeFlowApplication.getAppInstance().getAccountInfo();
		
		String phoneNum = getIntent().getStringExtra("phone");
		if(phoneNum != null) {
			Tel = phoneNum;
		}
//		String flowvalue = getIntent().getStringExtra("flow");
//		if(flowvalue != null) {
		
		Flow = accountInfo.getFlowcoins() + "";
//		}
		
		rlFlowCost = (RelativeLayout) findViewById(R.id.rl_flow_cost);
		rlFlowCost.setVisibility(View.GONE);
		
		tvFlowCost = (TextView) findViewById(R.id.tv_flow_cost);
		
		tvFlow = (TextView) findViewById(R.id.tv_flow);
		tvFlow.setText(Flow);
		
		btnStartLottery = (Button) findViewById(R.id.iv_start_lottery);
		btnStartLottery.setVisibility(View.VISIBLE);
		btnStartLottery.setOnClickListener(this);
		btnStartLottery.setBackgroundResource(R.color.scratch_bg_red);
		
		ivCover = (ImageView) findViewById(R.id.iv_cover);
		ivCover.setVisibility(View.VISIBLE);
//		ivCover.setOnClickListener(this);
		LayoutParams coverlp = ivCover.getLayoutParams();
		coverlp.width  = DisplayUtil.getSize(this, 688);
		coverlp.height = DisplayUtil.getSize(this, 488);
		ivCover.setLayoutParams(coverlp);
		
		
		stvCard = (ScratchTextView) findViewById(R.id.stv_card);
		LayoutParams cardlp = stvCard.getLayoutParams();
		cardlp.width  = DisplayUtil.getSize(this, 688);
		cardlp.height = DisplayUtil.getSize(this, 488);
		stvCard.setLayoutParams(cardlp);
		stvCard.initScratchCard(R.drawable.scratch_bg, 0, DisplayUtil.getSize(this, 50), 1f);
		stvCard.setCompletePercent(45);
		stvCard.setOnCompletedListener(new OnCompletedListener() {
			@Override
			public void OnCompleted() {
				handler.post(new Runnable() {
					
					@Override
					public void run() {
						tvFlow.setText(Flow);
						ivCover.setVisibility(View.GONE);
						btnStartLottery.setText("再刮一次");
						btnStartLottery.setTextColor(getResources().getColor(R.color.scratch_bg_red));
						btnStartLottery.setBackgroundResource(R.color.scratch_bg_yellow);
						btnStartLottery.setVisibility(View.VISIBLE);
//						randomAward();
//						stvCard.resetScratchCard(R.drawable.scratch_bg, 0);
					}
				});
			}
		});
//		randomAward();
		
		gvAward = (GridView) findViewById(R.id.gv_award);
		makeFakeData(gvAward);
		
		Requester.getScratchConfig(true, handler);
		
		/*TextView hint = (TextView) findViewById(R.id.tv_flow_hint);
		hint.setOnClickListener(this);*/
	}
	
	private int getGridViewHeight(GridView gridView) {
		int count = gridView.getAdapter().getCount();
		int rowNum = (int)Math.ceil(count / (double)3);
		int height = DisplayUtil.getSize(this, 220) * rowNum  + ViewUtils.getGridViewVerticalSpacing(gridView) * (rowNum + 1);
		return height;
	}
	
	private static String[] items = {
		"iphone6",
		"海外流量卡",
		"运动手环",
		"巴厘浪漫七日游",
		"海陆双拼套餐",
		"罗技键鼠套装"
	};
	
	private static int[] resId = {
		/*R.drawable.iphone6,
		R.drawable.hwllk,
		R.drawable.ydsh,
		R.drawable.bldlm,
		R.drawable.hlsptc,
		R.drawable.ljjstz,*/
		R.drawable.ic_launcher,
		R.drawable.ic_launcher,
		R.drawable.ic_launcher,
		R.drawable.ic_launcher,
		R.drawable.ic_launcher,
		R.drawable.ic_launcher,
	};
	
	private void makeFakeData(GridView gv) {
		// 生成动态数组，并且转入数据
		ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < 6; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("ItemImage", resId[i]);// 添加图像资源的ID
			map.put("ItemText", items[i]);// 按序号做ItemText
			lstImageItem.add(map);
		}
		// 生成适配器的ImageItem <====> 动态数组的元素，两者一一对应
		SimpleAdapter saImageItems = new SimpleAdapter(this, lstImageItem,// 数据来源
				R.layout.grid_award_item,// night_item的XML实现
				// 动态数组与ImageItem对应的子项
				new String[] { "ItemImage", "ItemText" },
				// ImageItem的XML文件里面的一个ImageView,两个TextView ID
				new int[] { R.id.iv_item_image, R.id.tv_item_text });
		// 添加并且显示
		gv.setAdapter(saImageItems);
//		int gridHeight = getGridViewHeight(gv);
//		ViewUtils.setHeightPixel(gv, gridHeight);
	}
	
	private void randomAward(String pricename) {
		stvCard.setText(pricename);
		/*int i = RandomUtils.getRandom(20);
		if(i < 6) {
			stvCard.setText("恭喜您获得\n" + items[i]);
		} else {
			stvCard.setText("谢谢参与");
		}*/
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()) {
		case R.id.iv_start_lottery:
			Requester.scratchFlow(true, handler, accountInfo.getUserid());
//			startLottery();
			break;
		case R.id.tv_flow_hint:
			ivCover.setVisibility(View.VISIBLE);
			btnStartLottery.setVisibility(View.VISIBLE);
			break;
		}
		super.onClick(v);
	}
	
	private void startLottery(String pricename) {
		randomAward(pricename);
		LayoutParams cardlp = stvCard.getLayoutParams();
		stvCard.resetScratchCard(cardlp.width, cardlp.height, R.drawable.scratch_bg, 0);
		if(isRetry) {
//			ivCover.setVisibility(View.VISIBLE);
//			btnStartLottery.setVisibility(View.VISIBLE);
		} else {
			ivCover.setVisibility(View.GONE);
		}
		btnStartLottery.setVisibility(View.GONE);
		isRetry = true;
	}
	
	private String[] noAward = {
			"哎呀，离中奖就差一点",
			"搞什么灰机，又没刮到",
			"据说下雨天跟大奖更配哦",
			"很遗憾，请再接再厉",
			"又没中，什么仇什么怨？"
	};
	
	@Override
	public boolean handleMessage(Message msg) {
		// TODO Auto-generated method stub
		switch(msg.what) {
		case Requester.RESPONSE_TYPE_SCRATCH:
			if(msg.obj != null) {
				scratchflowResp resp = (scratchflowResp) msg.obj;
				if("0".equals(resp.status) || "0000".equals(resp.status)) {
					if(resp.award != null) {
						accountInfo.setFlowcoins(resp.flowcoins);
						Flow = resp.flowcoins;
						WeFlowApplication.getAppInstance().PersistAccountInfo(accountInfo);
						String awardname = resp.award.prizename;
						if(awardname == null || awardname.equals("刮刮卡0流量币")) {
							awardname = noAward[RandomUtils.getRandom(4) % 5];
						}
						startLottery(awardname);
					} else {
						Toast mtoast;
						mtoast = Toast.makeText(ScratchCardActivity.this,
								noAward[RandomUtils.getRandom(4) % 5], Toast.LENGTH_SHORT);
						mtoast.show();
					}
				} else if("2016".equals(resp.status)) {
					OrderDialog.Dialog(this, "刮奖次数已用完", true);
//					PromptDialog.Alert(MainActivity.class, "刮奖次数已用完");
				}
			} else {
				PromptDialog.Alert(ScratchCardActivity.class, "您的网络不给力啊！");
			}
			break;
		case Requester.RESPONSE_TYPE_SCRATCH_CONFIG:
			if(msg.obj != null) {
				scratchConfigResp configresp = (scratchConfigResp) msg.obj;
				if("0".equals(configresp.status) || "0000".equals(configresp.status)) {
					float cost = 0.0f;
					try {
						cost = Float.parseFloat(configresp.cost);
					} catch(Exception e) {
						e.printStackTrace();
					}
					if(cost != 0) {
						rlFlowCost.setVisibility(View.VISIBLE);
						tvFlowCost.setText(Math.abs(cost) + "");
					}
				}
			}
			 
			break;
		}
		return false;
	}

	@Override
	public int subContentViewId() {
		// TODO Auto-generated method stub
		return R.layout.activity_scratchcard;
	}

}
