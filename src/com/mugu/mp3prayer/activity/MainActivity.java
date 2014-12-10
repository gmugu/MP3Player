package com.mugu.mp3prayer.activity;

import com.mugu.mp3prayer.R;
import com.mugu.mp3prayer.fragment.MainContentFragment;
import com.mugu.mp3prayer.service.PlayService;
import com.mugu.mp3prayer.sliding_menu.SlidingMenu;
import com.mugu.mp3prayer.sliding_menu.CustomViewAbove.OnMenuMovingListener;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.TextView;

public class MainActivity extends FragmentActivity {

	private String TAG = getClass().getSimpleName();
	// 主界面偏移比例
	private final float VISIBLE_OFFSET_SCALE = 0.8f;
	// 内容缩放比例
	private final float CONTENT_SCALE = 0.8f;
	// 菜单缩放比例
	private final float MENU_SCALE = 0.6f;

	private static MainActivity mActivity;
	private SlidingMenu slidingMenu;
	TextView tv;
	FrameLayout mainContentView;
	private int DisplayWidth;
	private PlayService.MyBinder binder;

	private ServiceConnection conn = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			binder = (PlayService.MyBinder) service;
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
		}

	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new MainContentFragment()).commit();
		}
		mActivity = this;

		mainContentView = (FrameLayout) findViewById(R.id.container);
		// 获取屏幕宽高
		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		DisplayWidth = metric.widthPixels;

		// test code
		tv = new TextView(this);
		tv.setBackgroundColor(0x9944aa10);
		tv.setText("菜单项");
		tv.setTextSize(30);
		tv.setGravity(Gravity.CENTER);

		initSlidingMenu();
		initService();

	}

	public static MainActivity getInstance() {
		return mActivity;

	}

	public PlayService.MyBinder getPlayServiceBinder() {
		return binder;
	}

	public void addFragment() {

	}

	private void initService() {
		Intent service = new Intent(this, PlayService.class);
		bindService(service, conn, BIND_AUTO_CREATE);
	}

	private void initSlidingMenu() {
		slidingMenu = new SlidingMenu(this);
		slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		slidingMenu.setMode(SlidingMenu.RIGHT);
		slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		slidingMenu.setTouchModeBehind(SlidingMenu.TOUCHMODE_FULLSCREEN);
		slidingMenu.setBehindWidth((int) (DisplayWidth * VISIBLE_OFFSET_SCALE));
		slidingMenu.setFadeEnabled(false);
		slidingMenu.setBehindScrollScale(1);

		slidingMenu.setMenu(tv);
		slidingMenu.setbottomBackgroundColor(0xff990912);
		slidingMenu.setOnMenuMovingListener(new OnMenuMovingListener() {
			@Override
			public void onMenuMoving(int position, float positionOffset,
					int positionOffsetPixels) {
				// 0~1变化的浮点数
				float percentage = positionOffset / VISIBLE_OFFSET_SCALE;
				// 主界面的变化
				float contentScale = 1f - percentage * (1f - CONTENT_SCALE);
				mainContentView.setScaleY(contentScale);
				mainContentView.setScaleX(contentScale);
				mainContentView.setTranslationX((float) (mainContentView
						.getWidth()) * (1f - contentScale) / 2f);
				// 菜单的变化
				float menuScale = MENU_SCALE + (1f - MENU_SCALE) * percentage;
				tv.setScaleX(menuScale);
				tv.setScaleY(menuScale);
				tv.setTranslationX((float) (-tv.getWidth()) * (1f - menuScale)
						/ 2f);
			}
		});
	}

	// @Override
	// public boolean onKeyDown(int keyCode, KeyEvent event) {
	//
	// switch (keyCode) {
	// // case KeyEvent.KEYCODE_BACK:
	// //
	// // if (slidingMenu.isMenuShowing()) {
	// // slidingMenu.showContent();
	// // } else {
	// // Intent intent = new Intent(Intent.ACTION_MAIN);
	// // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	// // intent.addCategory(Intent.CATEGORY_HOME);
	// // startActivity(intent);
	// // }
	// // break;
	//
	// case KeyEvent.KEYCODE_MENU:
	//
	// slidingMenu.toggle();
	//
	// break;
	// }
	//
	// return false;
	// }

}
