package com.cmmobi.railwifi.fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cmmobi.railwifi.R;
import com.cmmobi.railwifi.activity.AlbumDetailActivity;
import com.cmmobi.railwifi.activity.RecommendationActivity;
import com.cmmobi.railwifi.network.GsonResponseObject;
import com.cmmobi.railwifi.network.GsonResponseObject.AlumbElem;
import com.cmmobi.railwifi.network.GsonResponseObject.SubAlumbElem;
import com.cmmobi.railwifi.network.Requester;
import com.cmmobi.railwifi.utils.CmmobiClickAgentWrapper;
import com.cmmobi.railwifi.utils.DisplayUtil;
import com.cmmobi.railwifi.utils.StringUtils;
import com.cmmobi.railwifi.utils.ViewUtils;
import com.cmmobi.railwifi.view.TagTextView;
import com.idunnololz.widgets.AnimatedExpandableListView;
import com.idunnololz.widgets.AnimatedExpandableListView.AnimatedExpandableListAdapter;
import com.nostra13.universalimageloader.api.MyImageLoader;
import com.nostra13.universalimageloader.api.RoundedBorderBitmapDisplayer;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import de.greenrobot.event.EventBus;

public class PreviousRecommendationFragment extends Fragment implements /*Callback,*/ OnClickListener {
	
	private Handler handler;
	private AnimatedExpandableListView listView;
	private PriviousExpandableListAdapter adapter;
	List<GroupItem> groupItemList = new ArrayList<PreviousRecommendationFragment.GroupItem>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		adapter = new PriviousExpandableListAdapter(getActivity());
		adapter.setData(groupItemList);
//		handler = new Handler(this);
//		Requester.requestRecommendList(handler);
		EventBus.getDefault().registerSticky(this);;
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		EventBus.getDefault().unregister(this);
		super.onDestroy();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			 ViewGroup container,  Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_previous_period, null);
		
		initViews(view);
		return view;
	}
	
	private void initViews(View view) {
		listView = (AnimatedExpandableListView) view.findViewById(R.id.listView);
		listView.setGroupIndicator(null);
		listView.setSelector(new ColorDrawable(Color.TRANSPARENT));

		listView.setAdapter(adapter);
		ViewUtils.setWidth(view.findViewById(R.id.view_recommendation_title), 631);
		ViewUtils.setMarginRight(view.findViewById(R.id.view_recommendation_title), 12);
	}
	
	public void collapseGroup() {
		if (adapter != null) {
			for (int i = 0;i < adapter.getGroupCount();i++) {
				boolean flag = listView.collapseGroup(i);
			}
		}
	}

	/*@Override
	public boolean handleMessage(Message msg) {
		// TODO Auto-generated method stub
		switch(msg.what) {
		case Requester.RESPONSE_TYPE_MEDIA_RECOMMANDLIST:
			if (msg.obj != null) {
				GsonResponseObject.recmmandListResp resp = (GsonResponseObject.recmmandListResp) msg.obj;
				List<GroupItem> groupItemList = new ArrayList<PreviousRecommendationFragment.GroupItem>();
				
				if ("0".equals(resp.status)) {
					GsonResponseObject.AlumbElem[] recommArray = resp.list;
					for (int i = 0; i < recommArray.length; i++) {
						GroupItem group = new GroupItem();
						group.title = recommArray[i].periods;
						List<SubAlumbElem> alumbElemList = new ArrayList<SubAlumbElem>();
						SubAlumbElem[] alumbElemArray = recommArray[i].sublist;
						Collections.addAll(alumbElemList, alumbElemArray);
						group.items = alumbElemList;
						groupItemList.add(group);
					}
					
					adapter.setData(groupItemList);
					listView.setAdapter(adapter);
				}
			}
			break;
		}
		return false;
	}*/
	
	public void onEvent(GsonResponseObject.AlumbElem[] recommArray) {
		Log.d("=AAA=","PreviousRecom onEvent in");
		groupItemList.clear();
		for (int i = 0; i < recommArray.length; i++) {
			GroupItem group = new GroupItem();
			group.title = recommArray[i].periods;
			List<SubAlumbElem> alumbElemList = new ArrayList<SubAlumbElem>();
			SubAlumbElem[] alumbElemArray = recommArray[i].sublist;
			Collections.addAll(alumbElemList, alumbElemArray);
			group.items = alumbElemList;
			groupItemList.add(group);
		}
		
		adapter.notifyDataSetChanged();
	}
	
	private static class GroupItem {
        String title;
        List<SubAlumbElem> items = new ArrayList<SubAlumbElem>();
    }
	
	class ChildHolder {
		public ImageView ivRecommendation;
		public TextView tvIntro;
		public TextView tvTag;
		
	}
	
	private static class GroupHolder {
        TextView title;
        View ivTag;
    }
	
	private class PriviousExpandableListAdapter extends AnimatedExpandableListAdapter {

		private LayoutInflater inflater;
        private Context context;
        private List<GroupItem> items;
        MyImageLoader imageLoader = null;
    	DisplayImageOptions imageLoaderOptions = null;
        
        public PriviousExpandableListAdapter(Context context) {
        	this.context = context;
            inflater = LayoutInflater.from(context);
            
            imageLoader = MyImageLoader.getInstance();

    		imageLoaderOptions = new DisplayImageOptions.Builder()
    				.cacheInMemory(true)
    				.cacheOnDisc(true)
    				.imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
				    .bitmapConfig(Bitmap.Config.RGB_565)
//    				.displayer(new SimpleBitmapDisplayer())
    				.displayer(new RoundedBorderBitmapDisplayer(DisplayUtil.getSize(context, 10), 0xfff5813c, DisplayUtil.getSize(context, 2)))
//    				.displayer(new RoundedBitmapDisplayer(DisplayUtil.getSize(context, 10)))// 圆角图片
    				.build();
       }

       public void setData(List<GroupItem> items) {
           this.items = items;
       }
       
       @Override
       public GroupItem getGroup(int groupPosition) {
           return items.get(groupPosition);
       }

       @Override
       public int getGroupCount() {
           return items.size();
       }

       @Override
       public long getGroupId(int groupPosition) {
           return groupPosition;
       }

       @Override
       public SubAlumbElem getChild(int groupPosition, int childPosition) {
           return items.get(groupPosition).items.get(childPosition);
       }

       @Override
       public long getChildId(int groupPosition, int childPosition) {
           return childPosition;
       }

       @Override
       public boolean hasStableIds() {
           return true;
       }

       @Override
       public boolean isChildSelectable(int arg0, int arg1) {
           return true;
       }

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			GroupHolder holder;
            GroupItem item = getGroup(groupPosition);
            if (convertView == null) {
                holder = new GroupHolder();
                convertView = inflater.inflate(R.layout.item_recommendation_header, parent, false);
                holder.title = (TextView) convertView.findViewById(R.id.tv_recommendation_title);
                holder.ivTag = (View) convertView.findViewById(R.id.iv_tag);
                
                ViewUtils.setSize(holder.ivTag, 53, 53);
                holder.title.setTextSize(DisplayUtil.textGetSizeSp(context, 28));
                convertView.setTag(holder);
                
        		ViewUtils.setSize(holder.title, 631,69);
        		ViewUtils.setMarginRight(holder.title, 12);
            } else {
                holder = (GroupHolder) convertView.getTag();
            }
            
            if (isExpanded) {
            	holder.ivTag.setBackgroundResource(R.drawable.icon_time_selected);
            	holder.title.setBackgroundResource(R.drawable.bg_recommendation_header_text);
            } else {
            	holder.ivTag.setBackgroundResource(R.drawable.icon_time);
            	holder.title.setBackgroundResource(R.drawable.bg_recommendation_header_text_collapse);
            }
            holder.title.setText(item.title);
            
            return convertView;
		}

		@Override
		public View getRealChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ChildHolder holder;
			SubAlumbElem item = getChild(groupPosition, childPosition);
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.item_recommendation, null);
				holder = new ChildHolder();
				holder.ivRecommendation = (ImageView) convertView.findViewById(R.id.iv_recommendation_img);
				holder.tvIntro = (TextView) convertView.findViewById(R.id.tv_recommendation_intro);
				holder.tvTag = (TextView) convertView.findViewById(R.id.tv_recommendation_tag);
				ViewUtils.setSize(convertView.findViewById(R.id.rl_recommendation_img), 631, 354);
				ViewUtils.setHeight(holder.tvTag, 48);
				ViewUtils.setMarginRight(convertView.findViewById(R.id.rl_recommendation_img), 12);
				ViewUtils.setMarginTop(convertView.findViewById(R.id.view_bottom), 24);
				ViewUtils.setMarginTop(holder.tvIntro, 12);
				ViewUtils.setMarginTop(holder.tvTag, 12);
				ViewUtils.setMarginLeft(holder.tvTag, 2);
				holder.tvIntro.setTextSize(DisplayUtil.textGetSizeSp(context, 28));
				holder.tvTag.setTextSize(DisplayUtil.textGetSizeSp(context, 30));
				convertView.setTag(holder);
			} else {
				holder = (ChildHolder) convertView.getTag();
			}
			
			imageLoader.displayImage(item.img_path, holder.ivRecommendation,imageLoaderOptions);
			if (!StringUtils.isEmpty(item.tag)) {
				holder.tvTag.setVisibility(View.VISIBLE);
				holder.tvTag.setText(item.tag);
				String color = item.color;
	//			int colorBg = Color.parseColor(color);
	//			holder.tvTag.setBgColor(colorBg);
				if("1".equals(color)){
					holder.tvTag.setBackgroundResource(R.drawable.bg_tag_color_a);
				}else if("2".equals(color)){
					holder.tvTag.setBackgroundResource(R.drawable.bg_tag_color_b);
				}else if("3".equals(color)){
					holder.tvTag.setBackgroundResource(R.drawable.bg_tag_color_c);
				}else if("4".equals(color)){
					holder.tvTag.setBackgroundResource(R.drawable.bg_tag_color_d);
				}else if("5".equals(color)){
					holder.tvTag.setBackgroundResource(R.drawable.bg_tag_color_e);
				}
			} else {
				holder.tvTag.setVisibility(View.GONE);
			}
			holder.tvIntro.setText(item.introduction);
			
			ChildElem childElem = new ChildElem();
			childElem.groupPos = groupPosition;
			childElem.item = item;
			holder.ivRecommendation.setTag(childElem);
			holder.ivRecommendation.setOnClickListener(PreviousRecommendationFragment.this);
            return convertView;
		}

		@Override
		public int getRealChildrenCount(int groupPosition) {
			// TODO Auto-generated method stub
			return items.get(groupPosition).items.size();
		}
	}
	
	class ChildElem {
		int groupPos;
		SubAlumbElem item;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()) {
		case R.id.iv_recommendation_img:
			ChildElem childItem = (ChildElem) v.getTag();
			GroupItem groupItem = adapter.getGroup(childItem.groupPos);
			Intent intent = new Intent(getActivity(),AlbumDetailActivity.class);
			intent.putExtra("mediaid", childItem.item.object_id);
			intent.putExtra("title", groupItem.title);
			startActivity(intent);
			CmmobiClickAgentWrapper.onEvent(getActivity(), "av_review","label", childItem.item.object_id, "label2", childItem.item.tag);
			break;
		}
	}
	
	
}
