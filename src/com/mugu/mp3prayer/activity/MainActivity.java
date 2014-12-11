package com.mugu.mp3prayer.activity;

import java.io.IOException;
import java.io.InputStream;

import com.mugu.mp3prayer.R;
import com.mugu.mp3prayer.config.Config;
import com.mugu.mp3prayer.fragment.MainContentFragment;
import com.mugu.mp3prayer.fragment.MenuFragment;
import com.mugu.mp3prayer.service.PlayService;
import com.mugu.mp3prayer.sliding_menu.SlidingMenu;
import com.mugu.mp3prayer.sliding_menu.CustomViewAbove.OnMenuMovingListener;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
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
	private FrameLayout mainContentFrame;
	private FrameLayout menuContentFrame;
	private PlayService.MyBinder binder;
	boolean isFinish = false;
	public int DisplayWidth;
	public int DisplayHeigh;

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
		setContentView(R.layout.main_content_frame);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new MainContentFragment()).commit();
		}
		mActivity = this;

		getDisplayBound();
		initSlidingMenu();
		initService();

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			//
			// if (slidingMenu.isMenuShowing()) {
			// slidingMenu.showContent();
			// } else {
			// Intent intent = new Intent(Intent.ACTION_MAIN);
			// intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			// intent.addCategory(Intent.CATEGORY_HOME);
			// startActivity(intent);
			// }
			finish();
			isFinish = true;
			break;

		case KeyEvent.KEYCODE_MENU:

			slidingMenu.toggle();

			break;
		}

		return false;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unbindService(conn);
	}

	public static MainActivity getInstance() {
		return mActivity;

	}

	public boolean getIsFinish() {
		return isFinish;

	}

	public PlayService.MyBinder getPlayServiceBinder() {
		return binder;
	}

	// 获取屏幕大小
	private void getDisplayBound() {
		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		DisplayWidth = metric.widthPixels;
		DisplayHeigh = metric.heightPixels;
	}

	private void initService() {
		Intent service = new Intent(this, PlayService.class);
		bindService(service, conn, BIND_AUTO_CREATE);
	}

	private void initSlidingMenu() {
		slidingMenu = new SlidingMenu(this);
		getSupportFragmentManager().beginTransaction()
				.add(R.id.menu_frame, new MenuFragment()).commit();

		mainContentFrame = (FrameLayout) findViewById(R.id.container);
		menuContentFrame = (FrameLayout) (getLayoutInflater().inflate(
				R.layout.menu_frame, null)).findViewById(R.id.menu_frame);
		slidingMenu.setbottomBackground(new BitmapDrawable(getResources(),
				getImageFromAssetsFile(Config.getInstent()
						.getBottomBackground())));

		slidingMenu.setMenu(R.layout.menu_frame);
		slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		slidingMenu.setMode(SlidingMenu.RIGHT);
		slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		slidingMenu.setTouchModeBehind(SlidingMenu.TOUCHMODE_FULLSCREEN);
		slidingMenu.setBehindWidth((int) (DisplayWidth * VISIBLE_OFFSET_SCALE));
		slidingMenu.setFadeEnabled(false);
		slidingMenu.setBehindScrollScale(1);
		slidingMenu.setOnMenuMovingListener(new OnMenuMovingListener() {
			@Override
			public void onMenuMoving(int position, float positionOffset,
					int positionOffsetPixels) {
				// 从0~1的浮点数
				float percentage = positionOffset / VISIBLE_OFFSET_SCALE;
				// 主界面的变化
				float contentScale = 1f - percentage * (1f - CONTENT_SCALE);
				mainContentFrame.setScaleY(contentScale);
				mainContentFrame.setScaleX(contentScale);
				mainContentFrame.setTranslationX((float) (mainContentFrame
						.getWidth()) * (1f - contentScale) / 2f);
				// 菜单的变化
				float menuScale = MENU_SCALE + (1f - MENU_SCALE) * percentage;
				menuContentFrame.setScaleX(menuScale);
				menuContentFrame.setScaleY(menuScale);
				menuContentFrame.setTranslationX((float) (-menuContentFrame
						.getWidth()) * (1f - menuScale) / 2f);
			}
		});
	}

	private Bitmap getImageFromAssetsFile(String fileName) {
		Bitmap image = null;
		AssetManager am = getResources().getAssets();
		try {
			InputStream is = am.open(fileName);
			image = BitmapFactory.decodeStream(is);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return image;

	}

}
