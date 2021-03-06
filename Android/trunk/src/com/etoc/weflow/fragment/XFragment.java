package com.etoc.weflow.fragment;

import com.etoc.weflow.activity.MainActivity;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class XFragment<T> extends Fragment implements Callback{

	private static final String TAG=XFragment.class.getSimpleName();
	protected Handler handler;
	protected String userID;
	
	protected static final int INDEX_HOMEPAGE = 0;
	protected static final int INDEX_BANK     = 1;
	protected static final int INDEX_DISCOVER = 2;
	protected static final int INDEX_ME       = 3;
	
	@Override
	public void onAttach(Activity a){
		super.onAttach(a);
		Log.v(TAG, "fragment-" + this.getClass().getName() + " onAttach - " + a.getClass().getName());
	}
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		handler = new Handler(this);
		if(savedInstanceState==null){
			Log.v(TAG, "fragment-" + this.getClass().getName() + " onCreate - savedInstanceState is null");
		}else{
//			Log.v(TAG, "fragment-" + this.getClass().getName() + " onCreate - savedInstanceState/SAVE_KEY:" + savedInstanceState.getString(MainActivity.SAVE_KEY));
		}
		
	}
	
	@Override
	public View onCreateView(LayoutInflater li, ViewGroup vg, Bundle savedInstanceState) {
		View view =  super.onCreateView(li, vg, savedInstanceState);
		if(savedInstanceState==null){
			Log.v(TAG, "fragment-" + this.getClass().getName() + " onCreateView - savedInstanceState is null");
		}else{
//			Log.v(TAG, "fragment-" + this.getClass().getName() + " onCreateView - savedInstanceState/SAVE_KEY:" + savedInstanceState.getString(MainActivity.SAVE_KEY));
		}
		
		return view;
	}
	
	
	@Override
	public void onViewCreated(View v, Bundle savedInstanceState) {
	
		super.onViewCreated(v, savedInstanceState);
		if(savedInstanceState==null){
			Log.v(TAG, "fragment-" + this.getClass().getName() + " onViewCreated - savedInstanceState is null");
		}else{
//			Log.v(TAG, "fragment-" + this.getClass().getName() + " onViewCreated - savedInstanceState/SAVE_KEY:" + savedInstanceState.getString(MainActivity.SAVE_KEY));
		}
		
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if(savedInstanceState==null){
			Log.v(TAG, "fragment-" + this.getClass().getName() + " onActivityCreated - savedInstanceState is null");
		}else{
//			Log.v(TAG, "fragment-" + this.getClass().getName() + " onActivityCreated - savedInstanceState/SAVE_KEY:" + savedInstanceState.getString(MainActivity.SAVE_KEY));
		}
		
		
	}
	
	@Override
	public void onViewStateRestored(Bundle savedInstanceState) {
		super.onViewStateRestored(savedInstanceState);
		if(savedInstanceState==null){
			Log.v(TAG, "fragment-" + this.getClass().getName() + " onViewStateRestored - savedInstanceState is null");
		}else{
//			Log.v(TAG, "fragment-" + this.getClass().getName() + " onViewStateRestored - savedInstanceState/SAVE_KEY:" + savedInstanceState.getString(MainActivity.SAVE_KEY));
		}
		
	}
	
	@Override
	public void onStart() {
		super.onStart();
		Log.v(TAG, "fragment-" + this.getClass().getName() + " onStart");
	}
	
	@Override
	public void onResume() {
		super.onResume();
		Log.v(TAG, "fragment-" + this.getClass().getName() + " onResume");
	}
	
	public int getIndex() {
		return 0;
	}
	
	public abstract void onShow();

	public Handler getHandler() {
		return handler;
	}
	
	/**
	 * 当fragment有数据需要更新时,子类可重载此函数
	 */
	public void updateViews(T data){
		
	}
	
	
	/**
	 * 当fragment有数据需要更新时,子类可重载此函数
	 */
	public void stopCallBack(T data){
		
	}
	
	/**
	 * 切换内容区fragment
	 */
	public void switchContent(final XFragment<?> fragment) {
		if (getActivity() == null)
			return;
		if(getActivity() instanceof MainActivity){
			MainActivity mainActivity = (MainActivity) getActivity();
			mainActivity.switchContent(fragment);
		}else{
			Log.e(TAG, "switchContent error getActivity="+getActivity());
		}
	}	
	
	/**
	 * 显示菜单
	 */
	protected void showMenu(){
		if (getActivity() == null)
			return;
		if(getActivity() instanceof MainActivity){
			MainActivity slidingActivity = (MainActivity) getActivity();
//			slidingActivity.showMenu();
		}
	}
	
	/**
	 * 显示内容
	 */
	protected void showContent(){
		if (getActivity() == null)
			return;
		if(getActivity() instanceof MainActivity){
			MainActivity slidingActivity = (MainActivity) getActivity();
//			slidingActivity.showContent();
		}
	}
	
	/*protected SlidingMenu getSlidingMenu() {
		if (getActivity() == null)
			return null;
		MainActivity act = (MainActivity) getActivity();
		return act.getSlidingMenu();
	}*/

	@Override
	public boolean handleMessage(Message msg) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
