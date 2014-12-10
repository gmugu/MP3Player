package com.mugu.mp3prayer.fragment;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import com.mugu.mp3prayer.activity.MainActivity;
import com.mugu.mp3prayer.service.PlayService;
import com.mugu.mp3prayer.service.PlayService.OnPlayStateChangeListener;
import com.mugu.mp3prayer.utils.MyApplication;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class ChildOfDownload_DoneDownload extends Fragment {

	private final String TAG = getClass().getSimpleName();
	LinearLayout mView;
	PlayService.MyBinder binder;
	Button bn1;
	Button bn2;
	Button bn3;
	Button bn4;
	Button bn5;
	Button bn6;
	SeekBar seekBar;
	Handler handler;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.d("ChildOfDownload_DoneDownload", "onCreateView");
		if (mView != null) {
			ViewGroup parent = (ViewGroup) mView.getParent();
			if (parent != null) {
				parent.removeView(mView);
			}
			return mView;
		} else {
			mView = new LinearLayout(getActivity());
			bn1 = new Button(getActivity());
			bn1.setText("play");
			bn2 = new Button(getActivity());
			bn2.setText("pause");
			bn3 = new Button(getActivity());
			bn3.setText("stop");
			bn4 = new Button(getActivity());
			bn4.setText("goon");
			bn5 = new Button(getActivity());
			bn5.setText("next");
			bn6 = new Button(getActivity());
			bn6.setText("pre");
			seekBar = new SeekBar(getActivity());

			handler = new Handler() {
				@Override
				public void handleMessage(Message msg) {
					if (msg.what == 0x123) {
						if (seekBar != null) {
							if (binder.getPlayer() == null) {
								Log.d(TAG, "binder.getPlayer()==null");
							}
							seekBar.setProgress(binder.getPlayer()
									.getCurrentPosition());
						}
					}
				}
			};
			mView.addView(bn1);
			mView.addView(bn2);
			mView.addView(bn3);
			mView.addView(bn4);
			mView.addView(bn5);
			mView.addView(bn6);
			mView.addView(seekBar, new LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.WRAP_CONTENT));
			binder = MainActivity.getInstance().getPlayServiceBinder();

			seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

				@Override
				public void onStopTrackingTouch(SeekBar seekBar) {

					binder.getPlayer().seekTo(seekBar.getProgress());
				}

				@Override
				public void onStartTrackingTouch(SeekBar seekBar) {
				}

				@Override
				public void onProgressChanged(SeekBar seekBar, int progress,
						boolean fromUser) {

				}
			});
			binder.setOnPlayStateChangeListener(new OnPlayStateChangeListener() {

				@Override
				public void onStop(MediaPlayer player) {
					seekBar.setProgress(0);
				}

				@Override
				public void onStartPlay(MediaPlayer player,
						HashMap<String, Object> currentPlayingMusic) {
					seekBar.setMax(player.getDuration());
					new Timer().schedule(new TimerTask() {

						@Override
						public void run() {
							handler.sendEmptyMessage(0x123);
							Log.d(TAG, "onTimerTask");
						}
					}, 0, 100);
				}

				@Override
				public void onPause(MediaPlayer player) {
				}

				@Override
				public void onGoon(MediaPlayer player) {
				}
			});
			bn1.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					binder.startPlayList(MyApplication.allMusicList, 0);
				}
			});
			bn2.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					binder.pause();
				}
			});
			bn3.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					binder.stop();
				}
			});
			bn4.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					binder.goon();
				}
			});
			bn5.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					binder.next();
				}
			});
			bn6.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					binder.previous();
				}
			});

			return mView;
		}

	}
}
