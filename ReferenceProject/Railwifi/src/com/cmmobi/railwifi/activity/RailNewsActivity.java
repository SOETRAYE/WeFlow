package com.cmmobi.railwifi.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

import com.cmmobi.railwifi.R;
import com.cmmobi.railwifi.adapter.MoviesListAdapter;
import com.cmmobi.railwifi.adapter.RailNewsAdapter;
import com.cmmobi.railwifi.dialog.PromptDialog;
import com.cmmobi.railwifi.network.GsonResponseObject;
import com.cmmobi.railwifi.network.GsonResponseObject.mediaElem;
import com.cmmobi.railwifi.network.GsonResponseObject.newsElem;
import com.cmmobi.railwifi.network.Requester;
import com.cmmobi.railwifi.utils.CmmobiClickAgentWrapper;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

/**
 * @author zhangwei
 * @email zhangwei@cmmobi.com
 * @date  2014-11-17
 */
public class RailNewsActivity extends TitleRootActivity implements OnRefreshListener2<ListView>{


	private static final int HANDLER_FLAG_LIST_NO_DATA = 0x17386718;
	private RelativeLayout rlNoNet;
	private PullToRefreshListView railNewsData;
	private ListView lv_railNewsData;
	private RailNewsAdapter railNewsListAdapter;
	private ArrayList<GsonResponseObject.newsElem> listItems;
	
	private boolean isHasNextPage = false;
	private int pageNo;
	
	@Override
	public int subContentViewId() {
		return R.layout.activity_railservice_railnews;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setTitleText("铁路资讯");
		
		rightButton.setVisibility(View.GONE);
		
		rlNoNet = (RelativeLayout) findViewById(R.id.rl_no_network);
		railNewsData = (PullToRefreshListView) findViewById(R.id.xlv_railnews_data);
		railNewsData.setShowIndicator(false);
		lv_railNewsData = railNewsData.getRefreshableView();
		railNewsData.setOnRefreshListener(this);
		
		railNewsListAdapter = new RailNewsAdapter(this);
		listItems = new ArrayList<GsonResponseObject.newsElem>();
		railNewsListAdapter.setData(listItems);
		pageNo = 1;
		Requester.requestNewsList(handler, pageNo);
		
//		moviesListAdapter.setData(listItems);
		
		lv_railNewsData.setAdapter(railNewsListAdapter);
		
		lv_railNewsData.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent detailIntent = new Intent(RailNewsActivity.this,NewsDetailActivity.class);
				GsonResponseObject.newsElem elem = (newsElem) lv_railNewsData.getAdapter().getItem(arg2);
				detailIntent.putExtra("news_id", elem.object_id);
				startActivity(detailIntent);
				CmmobiClickAgentWrapper.onEvent(RailNewsActivity.this, "t_news", elem.object_id);
			}
		});
		
		lv_railNewsData.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				
				return false;
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
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		// TODO Auto-generated method stub
		
		if(isHasNextPage){
			Requester.requestNewsList(handler, ++pageNo);
		}else{
//			refreshView.onRefreshComplete();
			handler.sendEmptyMessage(HANDLER_FLAG_LIST_NO_DATA);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch(v.getId()){
			
			default:{
				
			}	
		}
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
			railNewsData.onRefreshComplete();
			break;
		case Requester.RESPONSE_TYPE_NEWSLIST:
			GsonResponseObject.newsListResp r4 = (GsonResponseObject.newsListResp) msg.obj;
			if(r4!=null && r4.status!=null && r4.status.equals("0")){
				List<GsonResponseObject.newsElem> data_r4 = new ArrayList<GsonResponseObject.newsElem>();
				if(r4.list!=null && r4.list.length>0){
					for(int i=0; i<r4.list.length; i++){
						data_r4.add(r4.list[i]);
					}
				}

				listItems.addAll(data_r4);

				railNewsListAdapter.notifyDataSetChanged();
				
				isHasNextPage = "1".equals(r4.isNextPage);
			}else{
				if(listItems!=null && listItems.size()>0){
					PromptDialog.Dialog(this, "温馨提示", "网络异常", "确定");
				}else{
					rlNoNet.setVisibility(View.VISIBLE);
				}
				
			}
			
			railNewsData.onRefreshComplete();
			break;
		}
		return false;
	}
}
