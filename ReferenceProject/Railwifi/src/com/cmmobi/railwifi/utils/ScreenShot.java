package com.cmmobi.railwifi.utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.media.MediaMetadataRetriever;
import android.util.Log;
import android.view.View;

public class ScreenShot {

	// 获取指定Activity的截屏，保存到png文件

	static Bitmap takeScreenShot(Activity activity) {

		Log.i("TAG", "tackScreenShot");
		// View是你须要截图的View
		View view = activity.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap b1 = view.getDrawingCache();

		// 获取状况栏高度
		Rect frame = new Rect();
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		int statusBarHeight = frame.top;
		Log.i("TAG", "statusBarHeight = " + statusBarHeight);

		// 获取屏幕长和高
		int width = activity.getWindowManager().getDefaultDisplay().getWidth();
		int height = activity.getWindowManager().getDefaultDisplay().getHeight();

		// 去掉题目栏
		// Bitmap b = Bitmap.createBitmap(b1, 0, 25, 320, 455);
		Bitmap b = Bitmap.createBitmap(b1, 0, statusBarHeight, width, height
				- statusBarHeight);
		view.destroyDrawingCache();
		return b;
	}

	// 保存到sdcard
	private static void savePic(Bitmap b, String strFileName) {

		FileOutputStream fos = null;
		try {
			Log.i("TAG", "start savePic");
			fos = new FileOutputStream(strFileName);
			Log.i("TAG", "strFileName = " + strFileName);
			if (null != fos) {
				b.compress(Bitmap.CompressFormat.PNG, 90, fos);
				fos.flush();
				fos.close();
				Log.i("TAG", "save pic");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void shoot(Activity a, String b) {
		// ScreenShot.savePic(ScreenShot.takeScreenShot(a), "sdcard/xx.png");
		Log.i("TAG", "shot");
		Bitmap bitmap = ScreenShot.takeScreenShot(a);
		ScreenShot.savePic(bitmap, b);
	}

	private Bitmap createVideoThumbnail(String filePath) {
		Bitmap bitmap = null;
		android.media.MediaMetadataRetriever retriever = new android.media.MediaMetadataRetriever();
		try {
			// MODE_CAPTURE_FRAME_ONLY
			// retriever
			// .setMode(android.media.MediaMetadataRetriever.MODE_CAPTURE_FRAME_ONLY);
			// retriever.setMode(MediaMetadataRetriever.MODE_CAPTURE_FRAME_ONLY);
			retriever.setDataSource(filePath);
			// bitmap = retriever.captureFrame();
			String timeString = retriever
					.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
			long time = Long.parseLong(timeString) * 1000;
			Log.i("TAG", "time = " + time);
			bitmap = retriever.getFrameAtTime(time * 31 / 160); // 按视频长度比例选择帧
		} catch (IllegalArgumentException ex) {
			// Assume this is a corrupt video file
		} catch (RuntimeException ex) {
			// Assume this is a corrupt video file.
		} finally {
			try {
				retriever.release();
			} catch (RuntimeException ex) {
				// Ignore failures while cleaning up.
			}
		}
		return bitmap;
	}
}
