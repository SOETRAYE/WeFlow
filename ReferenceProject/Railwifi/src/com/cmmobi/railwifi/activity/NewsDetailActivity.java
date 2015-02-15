package com.cmmobi.railwifi.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cmmobi.railwifi.R;
import com.cmmobi.railwifi.adapter.RailTravelDetailListAdapter;
import com.cmmobi.railwifi.dialog.PromptDialog;
import com.cmmobi.railwifi.network.GsonResponseObject;
import com.cmmobi.railwifi.network.Requester;
import com.cmmobi.railwifi.utils.DisplayUtil;
import com.cmmobi.railwifi.utils.ViewUtils;
import com.nostra13.universalimageloader.api.MyImageLoader;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

/**
 * 资讯详情
 * @author wangjm@cmmobi.com
 *
 */
public class NewsDetailActivity extends TitleRootActivity {

	protected static final int HANDLER_FLAG_PIC_SHOW = 0x163761;
	private RelativeLayout rlNoNet;
	private TextView tvTitle;
	private ImageView ivImg;
	private TextView tvContent;
	private String newsId;
	MyImageLoader imageLoader = null;
	DisplayImageOptions imageLoaderOptions = null;
	
	@Override
	public boolean handleMessage(Message msg) {
		// TODO Auto-generated method stub
		switch(msg.what) {
		case HANDLER_FLAG_PIC_SHOW:
			String url = (String)msg.obj;
			Bundle b = msg.getData();
			
			if(url==null || b==null){
				break;
			}
			
			int w = b.getInt("w");
			int h = b.getInt("h");

			RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams)ivImg.getLayoutParams();
			
			lp.width = DisplayUtil.getScreenWidth(NewsDetailActivity.this) - DisplayUtil.getSize(NewsDetailActivity.this, 12)*2;
			if(w!=0 && h!=0 ){
				lp.height = lp.width * h/w;
			}else{
				lp.height = lp.width;	
			}
			
			ivImg.setLayoutParams(lp);
			ivImg.setScaleType(ScaleType.FIT_XY);
			imageLoader.displayImage(url, ivImg, imageLoaderOptions);
			break;
		case Requester.RESPONSE_TYPE_NEWSINFO:
			if (msg.obj != null) {
				final GsonResponseObject.newsInfoContent newsInfo = (GsonResponseObject.newsInfoContent) msg.obj;
				if ("0".equals(newsInfo.status)) {
					tvTitle.setText(newsInfo.title);
					tvContent.setText(newsInfo.content);
					
					new Thread(){
						
						@Override
						public void run() {
							// TODO Auto-generated method stub

							// TODO Auto-generated method stub
							 int h = 0; 
							 int w = 0;
							  
							try {
								    byte[] data = RailTravelDetailListAdapter.getImage(newsInfo.img_path);
								    String d = new String(data);
								    int length = data.length;
								    Bitmap bitMap = BitmapFactory.decodeByteArray(data, 0, length);
								    if(bitMap !=null){
									    w = bitMap.getWidth();
									    h = bitMap.getHeight();
								    }
								    //imageView.seti
								} catch (Error e) {
								    e.printStackTrace();
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

							Bundle bundle = new Bundle();
							bundle.putInt("w", w);
							bundle.putInt("h", h);
							Message msg = handler.obtainMessage(HANDLER_FLAG_PIC_SHOW, newsInfo.img_path);
							msg.setData(bundle);
							msg.sendToTarget();
						
						}
					}.start();
					
					

				}else{
					rlNoNet.setVisibility(View.VISIBLE);
				}
			}else{
				rlNoNet.setVisibility(View.VISIBLE);
			}
			break;
		}
		return false;
	}

	@Override
	public int subContentViewId() {
		// TODO Auto-generated method stub
		return R.layout.activity_news_detail;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setTitleText("铁路资讯");
		hideRightButton();
		
		initViews();
		
		newsId = getIntent().getStringExtra("news_id");
		
		Requester.requestNewsInfo(handler, newsId);
		
		imageLoader = MyImageLoader.getInstance();

		imageLoaderOptions = new DisplayImageOptions.Builder()
				.cacheInMemory(true)
				.cacheOnDisc(true)
				.imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
				.bitmapConfig(Bitmap.Config.RGB_565)
				.showImageOnLoading(R.drawable.pic_list_default)
				.showImageForEmptyUri(R.drawable.pic_list_default)
				.showImageOnFail(R.drawable.pic_list_default)
//				.displayer(new SimpleBitmapDisplayer())
				// .displayer(new CircularBitmapDisplayer()) 圆形图片
//				.displayer(new RoundedBitmapDisplayer(1))// 圆角图片
				.build();
	}
	
	private void initViews() {
		rlNoNet = (RelativeLayout) findViewById(R.id.rl_no_network);
		tvTitle = (TextView) findViewById(R.id.tv_news_title);
		ivImg = (ImageView) findViewById(R.id.iv_news_img);
		tvContent = (TextView) findViewById(R.id.tv_news_content);
		
		ViewUtils.setMarginTop(tvTitle, 24);
		ViewUtils.setMarginLeft(tvTitle, 24);
		ViewUtils.setMarginRight(tvTitle, 24);
		ViewUtils.setMarginTop(ivImg, 24);
		ViewUtils.setMarginLeft(ivImg, 24);
		ViewUtils.setMarginRight(ivImg, 24);
		ViewUtils.setMarginTop(tvContent, 24);
		ViewUtils.setMarginLeft(tvContent, 24);
		ViewUtils.setMarginRight(tvContent, 24);
		
		tvTitle.setTextSize(DisplayUtil.textGetSizeSp(this, 36));
		tvContent.setTextSize(DisplayUtil.textGetSizeSp(this, 30));
	}

}
