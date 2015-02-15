package com.cmmobi.railwifi.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cmmobi.railwifi.R;
import com.cmmobi.railwifi.activity.AlbumDetailActivity;
import com.cmmobi.railwifi.network.GsonResponseObject.SubAlumbElem;
import com.cmmobi.railwifi.utils.CmmobiClickAgentWrapper;
import com.cmmobi.railwifi.utils.DisplayUtil;
import com.cmmobi.railwifi.utils.StringUtils;
import com.cmmobi.railwifi.utils.ViewUtils;
import com.nostra13.universalimageloader.api.MyImageLoader;
import com.nostra13.universalimageloader.api.RoundedBorderBitmapDisplayer;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import dev.dworks.libs.astickyheader.SimpleSectionedListAdapter;
import dev.dworks.libs.astickyheader.SimpleSectionedListAdapter.Section;

public class RecommendationAdapter extends BaseAdapter implements OnClickListener {

	private LayoutInflater inflater;
	private Context context;
	private List<SubAlumbElem> alumbElemList;
	MyImageLoader imageLoader = null;
	DisplayImageOptions imageLoaderOptions = null;
	private SimpleSectionedListAdapter sectionAdapter;
	private ArrayList<Section> sectionArray;
	
	
	public RecommendationAdapter(Context context,List<SubAlumbElem> list) {
		this.context = context;
		inflater = LayoutInflater.from(context);
		alumbElemList = list;
		
		imageLoader = MyImageLoader.getInstance();

		imageLoaderOptions = new DisplayImageOptions.Builder()
				.cacheInMemory(true)
				.cacheOnDisc(true)
				.imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
				.bitmapConfig(Bitmap.Config.RGB_565)
//				.displayer(new SimpleBitmapDisplayer())
				.displayer(new RoundedBorderBitmapDisplayer(DisplayUtil.getSize(context, 10), 0xfff5813c, DisplayUtil.getSize(context, 2)))
//				.displayer(new RoundedBitmapDisplayer(DisplayUtil.getSize(context, 10)))// 圆角图片
				.build();
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return alumbElemList.size();
	}

	@Override
	public SubAlumbElem getItem(int position) {
		// TODO Auto-generated method stub
		return alumbElemList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
	public void setSectionAdapter(SimpleSectionedListAdapter adapter) {
		this.sectionAdapter = adapter;
	}
	
	public void setSectionArray(ArrayList<Section> sectionArr) {
		this.sectionArray = sectionArr;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
//		Log.d("=AAA=","recommendation getView postion = " + position);
		ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_recommendation, null);
			holder = new ViewHolder();
			holder.ivRecommendation = (ImageView) convertView.findViewById(R.id.iv_recommendation_img);
			holder.tvIntro = (TextView) convertView.findViewById(R.id.tv_recommendation_intro);
			holder.tvTag = (TextView) convertView.findViewById(R.id.tv_recommendation_tag);
			holder.viewBottom = (View) convertView.findViewById(R.id.view_bottom);
			ViewUtils.setSize(convertView.findViewById(R.id.rl_recommendation_img), 631, 354);
			ViewUtils.setHeight(holder.tvTag, 48);
			ViewUtils.setMarginRight(convertView.findViewById(R.id.rl_recommendation_img), 12);
			ViewUtils.setMarginTop(holder.tvIntro, 12);
			ViewUtils.setMarginTop(holder.viewBottom, 24);
			ViewUtils.setMarginTop(holder.tvTag, 12);
			ViewUtils.setMarginLeft(holder.tvTag, 2);
			holder.tvIntro.setTextSize(DisplayUtil.textGetSizeSp(context, 28));
			holder.tvTag.setTextSize(DisplayUtil.textGetSizeSp(context, 30));
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		if (sectionAdapter != null) {
			if (sectionAdapter.isSectionHeaderPosition(sectionAdapter.positionToSectionedPosition(position) + 1)) {
				holder.viewBottom.setVisibility(View.VISIBLE);
			} else {
				holder.viewBottom.setVisibility(View.INVISIBLE);
			}
		}
		
		holder.position = position;
		imageLoader.displayImage(alumbElemList.get(position).img_path, holder.ivRecommendation,imageLoaderOptions);
		if (!StringUtils.isEmpty(alumbElemList.get(position).tag)) {
			holder.tvTag.setVisibility(View.VISIBLE);
			holder.tvTag.setText(alumbElemList.get(position).tag);
			String color = alumbElemList.get(position).color;
	//		int colorBg = Color.parseColor(color);
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
//		holder.tvTag.setBgColor(colorBg);
		ChildElem childElem = new ChildElem();
		childElem.position = position;
		childElem.subElem = alumbElemList.get(position);
		holder.tvIntro.setText(alumbElemList.get(position).introduction);
		holder.ivRecommendation.setTag(childElem);
		holder.ivRecommendation.setOnClickListener(this);
		
		return convertView;
	}
	
	class ChildElem {
		int position;
		SubAlumbElem subElem; 
	}
	
	class ViewHolder {
		public ImageView ivRecommendation;
		public TextView tvIntro;
		public TextView tvTag;
		public View viewBottom;
		public int position;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()) {
		case R.id.iv_recommendation_img:
			ChildElem item = (ChildElem) v.getTag();
			Intent intent = new Intent(context,AlbumDetailActivity.class);
			intent.putExtra("mediaid", item.subElem.object_id);
			
			if (sectionArray != null) {
				int size = sectionArray.size();
				for (int i = 0; i < size; i++) {
					Section sec1 = sectionArray.get(i);
					if (item.position >= sec1.firstPosition) {
						if (i != size - 1) {
							Section sec2 = sectionArray.get(i + 1);
							if (item.position < sec2.firstPosition) {
								intent.putExtra("title", sec1.getTitle());
							}
						} else {
							intent.putExtra("title", sec1.getTitle());
						}
					}
				}
			}
			context.startActivity(intent);
			String tag = "";
			if (item.subElem.tag != null) {
				tag = item.subElem.tag;
			}
			CmmobiClickAgentWrapper.onEvent(context, "av_topic", "label", item.subElem.object_id, "label2", tag);
			break;
		}
	}

}
