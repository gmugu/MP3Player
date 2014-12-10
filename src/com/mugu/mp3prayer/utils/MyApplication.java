package com.mugu.mp3prayer.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Application;
import android.content.res.Configuration;
import android.util.Log;

public class MyApplication extends Application {

	private final String TAG = getClass().getSimpleName();
	public static ArrayList<HashMap<String, Object>> playMusicList = new ArrayList<HashMap<String, Object>>();
	public static ArrayList<HashMap<String, Object>> allMusicList = new ArrayList<HashMap<String, Object>>();
	public static HashMap<String, ArrayList<HashMap<String, Object>>> loveMusicList = new HashMap<String, ArrayList<HashMap<String, Object>>>();
	public static int playStatus;
	public static int musicPosition;

	@Override
	public void onTerminate() {
		super.onTerminate();
		Log.d(TAG, "onTerminate");

	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		Log.d(TAG, "onConfigurationChanged");
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
		Log.d(TAG, "onConfigurationChanged");
	}

	@Override
	public void onTrimMemory(int level) {
		super.onTrimMemory(level);
		Log.d(TAG, "onTrimMemory");
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(TAG, "onCreate");
	}

}
