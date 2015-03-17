package com.cmmobi.railwifi.activity;

import android.R.integer;
import android.app.Activity;
import android.app.PendingIntent.OnFinished;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.cmmobi.railwifi.R;
import com.cmmobi.railwifi.adapter.RailTravelDetailListAdapter;
import com.cmmobi.railwifi.utils.DisplayUtil;
import com.nostra13.universalimageloader.api.AnimateFirstDisplayListener;
import com.nostra13.universalimageloader.api.MyImageLoader;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;


/**
 * 放大显示头像界面
 * 
 * @author youtian
 * 
 */

public class PicShowActivity extends Activity implements OnClickListener {
	private ImageView iv_photo;
	private DisplayImageOptions options;
	protected MyImageLoader imageLoader;
	private ImageLoadingListener animateFirstListener;
	
	private RelativeLayout ll_portraitshow;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting_portraitshow);
		
		iv_photo = (ImageView)findViewById(R.id.iv_photo);
		iv_photo.setPadding(DisplayUtil.getSize(this, 6), DisplayUtil.getSize(this, 6), DisplayUtil.getSize(this, 6), DisplayUtil.getSize(this, 6));
		RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams)iv_photo.getLayoutParams();
		lp.width = DisplayUtil.getScreenWidth(this) - DisplayUtil.getSize(this, 12)*2;
	/*	int w = getIntent().getIntExtra("w", 0);
		int h = getIntent().getIntExtra("h", 0);
		if(w!=0 && h!=0 ){
			lp.height = lp.width * h/w;
		}else{
			lp.height = lp.width;	
		}*/
		
		lp.leftMargin = DisplayUtil.getSize(this, 12);
		lp.rightMargin = DisplayUtil.getSize(this, 12);
		lp.topMargin = DisplayUtil.getSize(this, 12);
		lp.bottomMargin = DisplayUtil.getSize(this, 12);
		iv_photo.setLayoutParams(lp);
		animateFirstListener = new AnimateFirstDisplayListener();
		imageLoader = MyImageLoader.getInstance();
		options = new DisplayImageOptions.Builder()
		.showImageForEmptyUri(R.drawable.content_pic_default)
		.showImageOnFail(R.drawable.content_pic_default)
		.showImageOnLoading(R.drawable.content_pic_default)
		.cacheInMemory(true)
		.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
		.displayer(new SimpleBitmapDisplayer())
		.build();
		
		ll_portraitshow = (RelativeLayout)findViewById(R.id.ll_portraitshow);
		ll_portraitshow.setOnClickListener(this);
		String imageUrl=getIntent().getStringExtra("imageUrl");
		imageLoader.displayImage(imageUrl, iv_photo, options, animateFirstListener);
		
	}

	
	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
		
	}

	@Override
	public void onStop(){
		super.onStop();
		RailTravelDetailListAdapter.clickable = true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		this.finish();
	}


	
}
