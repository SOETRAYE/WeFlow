package com.cmmobi.railwifi.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.cmmobi.railwifi.R;
import com.cmmobi.railwifi.adapter.JokeListAdapter;
import com.cmmobi.railwifi.dialog.PromptDialog;
import com.cmmobi.railwifi.network.GsonResponseObject;
import com.cmmobi.railwifi.network.GsonResponseObject.mediaElem;
import com.cmmobi.railwifi.network.Requester;
import com.cmmobi.railwifi.utils.CmmobiClickAgentWrapper;
import com.cmmobi.railwifi.utils.ConStant;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

/**
 * @author zhangwei
 * @email zhangwei@cmmobi.com
 * @date  2014-11-19
 */
public class JokeActivity extends TitleRootActivity implements OnRefreshListener2<ListView>{


	private static final int HANDLER_FLAG_LIST_NO_DATA = 0x17638681;
	private PullToRefreshListView xlv_joke_Data;
	private ListView lv_joke_Data;
	private JokeListAdapter jokesListAdapter;
	private ArrayList<GsonResponseObject.mediaElem> listItems;
	private ArrayList<GsonResponseObject.mediaElem> backUpListItems = new ArrayList<GsonResponseObject.mediaElem>();
	private final int REQUEST_TAG = 0xffcd;
	
	private boolean isHasNextPage = false;
	private boolean backisHasNextPage = false;
	
	private int pageNo;
	private int backUpPageNo;
	private String tagType = null;
	private String tagName = null;
	RelativeLayout rlNoNet;
	
	@Override
	public int subContentViewId() {
		return R.layout.activity_media_joke;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setTitleText("逗你玩");
		
		tagType = null;
		tagName = null;
		
		rightButton.setBackgroundResource(R.drawable.btn_tag);
		
		rlNoNet = (RelativeLayout) findViewById(R.id.rl_no_network);
		xlv_joke_Data = (PullToRefreshListView) findViewById(R.id.xlv_joke_data);
		xlv_joke_Data.setPullLabel("加载更多");
		xlv_joke_Data.setReleaseLabel("松开加载更多");
		xlv_joke_Data.setShowIndicator(false);
		lv_joke_Data = xlv_joke_Data.getRefreshableView();
		xlv_joke_Data.setOnRefreshListener(this);
		
		jokesListAdapter = new JokeListAdapter(this);
		listItems = new ArrayList<GsonResponseObject.mediaElem>();
		jokesListAdapter.setData(listItems);
		pageNo = 1;
		Requester.requestJokeList(handler, pageNo,tagType);
		
		lv_joke_Data.setAdapter(jokesListAdapter);
		
		lv_joke_Data.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				// TODO Auto-generated method stub
//				Log.d("=AAA=","onItemClick in position = " + position);
				
				GsonResponseObject.mediaElem elem = (mediaElem) lv_joke_Data.getAdapter().getItem(position);
				
				CmmobiClickAgentWrapper.onEvent(JokeActivity.this, "av_joke_list", "label", elem.source_id, "label2", elem.tag);
				Intent intent = new Intent(JokeActivity.this, JokeDetailActivity.class);
				intent.putExtra(ConStant.INTENT_MEDIA_ID, elem.media_id);
				startActivity(intent);
			}
		});
		
		
		
	}
	
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
	}
	
	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	
	
	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
		// TODO Auto-generated method stub
		refreshView.onRefreshComplete();
	}

	@Override
	public void onPullUpToRefresh(final PullToRefreshBase<ListView> refreshView) {
		// TODO Auto-generated method stub
		Log.d("=AAA=","onPullUpToRefresh isHasNextPage = " + isHasNextPage);
		if(isHasNextPage){
			Requester.requestJokeList(handler, ++pageNo, tagType);
		}else{
			/*handler.postDelayed(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					refreshView.onRefreshComplete();
				}
			}, 100);*/
			
//			refreshView.onRefreshComplete();
			handler.sendEmptyMessage(HANDLER_FLAG_LIST_NO_DATA);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.btn_title_right:
			backisHasNextPage = isHasNextPage;
			backUpPageNo = pageNo;
			Intent tagIntent = new Intent(this, TagActivity.class);
			tagIntent.putExtra(TagActivity.KEY_TYPE, GsonResponseObject.MEDIA_TYPE_JOKE);
			startActivityForResult(tagIntent, REQUEST_TAG);
			
			break;
		case R.id.btn_title_left:
			onBackPressed();
			break;
			default:{
				
			}	
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (resultCode == RESULT_OK) {
			if (requestCode == REQUEST_TAG) {
				if (data != null) {
					pageNo = 1;
					listItems.clear();
					tagType = data.getStringExtra(TagActivity.KEY_TAG_ID);
					tagName = data.getStringExtra(TagActivity.KEY_TAG_NAME);
					Requester.requestJokeList(handler, pageNo, tagType);
					setTitleText(tagName);
					jokesListAdapter.notifyDataSetChanged();
				}
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public boolean handleMessage(Message msg) {
		// TODO Auto-generated method stub
		switch(msg.what){
		case HANDLER_FLAG_LIST_NO_DATA:
			xlv_joke_Data.onRefreshComplete();
			break;
		case Requester.RESPONSE_TYPE_MEDIA_JOKELIST:
			if (msg.obj != null) {
				GsonResponseObject.mediaListResp r16 = (GsonResponseObject.mediaListResp) msg.obj;
				if(r16!=null && r16.status!=null && r16.status.equals("0")){
					List<GsonResponseObject.mediaElem> data_r11 = new ArrayList<GsonResponseObject.mediaElem>();
					if(r16.list!=null && r16.list.length>0){
						for(int i=0; i<r16.list.length; i++){
							data_r11.add(r16.list[i]);
						}
					}
	
					listItems.addAll(data_r11);
	
					jokesListAdapter.notifyDataSetChanged();
					
					isHasNextPage = "1".equals(r16.isNextPage);
					Log.d("=AAA=","isNextPage = " + r16.isNextPage);
					if (tagType == null) {
						backUpListItems.addAll(data_r11);
						backUpPageNo = pageNo;
					}
				} else {
					rlNoNet.setVisibility(View.VISIBLE);
					PromptDialog.Dialog(this, "温馨提示", "网络错误 ：" + r16.status, "确定");
				}
				
				xlv_joke_Data.onRefreshComplete();
			} else {
				rlNoNet.setVisibility(View.VISIBLE);
			}
			break;
		}
		return false;
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		if (tagType == null) {
			super.onBackPressed();
		} else {
			listItems.clear();
			listItems.addAll(backUpListItems);
			isHasNextPage = backisHasNextPage;
			pageNo = backUpPageNo;
			jokesListAdapter.notifyDataSetChanged();
			tagType = null;
			tagName = null;
			setTitleText("逗你玩");
		}
	}
}
