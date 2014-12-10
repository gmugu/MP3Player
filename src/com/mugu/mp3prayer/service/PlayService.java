package com.mugu.mp3prayer.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class PlayService extends Service {

	private final String TAG = getClass().getName();

	private MyBinder binder = new MyBinder();

	/**
	 * 播放模式
	 */
	public final static int MODE_ONE_LOOP = 1;
	public final static int MODE_ALL_LOOP = 2;
	public final static int MODE_RANDOM = 3;
	public final static int MODE_SEQUENCE = 4;
	/**
	 * 播放状态
	 */
	public final static int STATE_PLAYING = 5;
	public final static int STATE_PAUSE = 6;
	public final static int STATE_STOP = 7;

	private MediaPlayer player;
	private int currentMode = MODE_ALL_LOOP;
	private int currentState = STATE_STOP;
	private int currentPlayingMusicPosition = -1;
	private HashMap<String, Object> currentPlayingMusic;
	private ArrayList<HashMap<String, Object>> Playlist;
	private OnPlayStateChangeListener onPlayStateChangeListener;

	public interface OnPlayStateChangeListener {

		public void onStartPlay(MediaPlayer player,
				HashMap<String, Object> currentPlayingMusic);

		public void onPause(MediaPlayer player);

		public void onGoon(MediaPlayer player);

		public void onStop(MediaPlayer player);

	}

	@Override
	public void onCreate() {
		super.onCreate();
		player = new MediaPlayer();
	}

	@Override
	public IBinder onBind(Intent intent) {
		return binder;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		player.release();
	}

	public class MyBinder extends Binder {

		/**
		 * 传入要播放的列表
		 * 
		 */
		public void startPlayList(ArrayList<HashMap<String, Object>> list,
				int musicPosition) {
			if (list == null) {
				Log.e(TAG, "list == null");
				return;
			}
			if (list.size() <= 0) {
				Log.e(TAG, "播放列表没有歌");
				return;
			}
			if (musicPosition >= list.size()) {
				Log.e(TAG, "starMusicPosition处没有歌曲");
				return;
			}
			if (currentState != STATE_STOP) {
				stop();
			}
			Playlist = list;
			currentPlayingMusicPosition = musicPosition;
			play(currentPlayingMusicPosition);
			player.setOnCompletionListener(new OnCompletionListener() {

				@Override
				public void onCompletion(MediaPlayer mp) {
					next();
				}
			});
		}

		/**
		 * 唱吧，有人想听
		 */
		public void play(int position) {

			if (Playlist == null) {
				Log.e(TAG, "This is not playing list!");
				return;
			}
			currentPlayingMusic = Playlist.get(position);
			player.reset();
			try {
				player.setDataSource(currentPlayingMusic.get("srcPath")
						.toString());
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			player.prepareAsync();
			player.setOnPreparedListener(new OnPreparedListener() {

				@Override
				public void onPrepared(MediaPlayer mp) {
					if (player != null) {
						player.start();
						currentState = STATE_PLAYING;
						if (onPlayStateChangeListener != null) {
							onPlayStateChangeListener.onStartPlay(player,
									currentPlayingMusic);
						}
					}
				}
			});
		}

		/**
		 * 暂停
		 */
		public void pause() {
			if (currentState == STATE_PLAYING) {
				player.pause();
				currentState = STATE_PAUSE;
			}
			if (onPlayStateChangeListener != null) {
				onPlayStateChangeListener.onPause(player);
			}
		}

		/**
		 * 继续
		 */
		public void goon() {
			if (currentState == STATE_PAUSE) {
				player.start();
				currentState = STATE_PLAYING;
			}
			if (onPlayStateChangeListener != null) {
				onPlayStateChangeListener.onGoon(player);
			}
		}

		/**
		 * 别唱了
		 */
		public void stop() {
			if (currentState != STATE_STOP) {
				player.stop();
				currentState = STATE_STOP;
			}
			if (onPlayStateChangeListener != null) {
				onPlayStateChangeListener.onStop(player);
			}
		}

		/**
		 * 后一首
		 */
		public void next() {
			if (Playlist == null) {
				Log.e(TAG, "This is not playing list!");
				return;
			}
			switch (getPlayMode()) {
			case MODE_ALL_LOOP:
				currentPlayingMusicPosition = (currentPlayingMusicPosition + 1)
						% Playlist.size();
				play(currentPlayingMusicPosition);
				break;
			case MODE_ONE_LOOP:
				play(currentPlayingMusicPosition);
				break;
			case MODE_RANDOM:
				currentPlayingMusicPosition = (int) (Playlist.size() * Math
						.random());
				play(currentPlayingMusicPosition);
				break;
			case MODE_SEQUENCE:
				currentPlayingMusicPosition++;
				if (currentPlayingMusicPosition >= Playlist.size()) {
					stop();
				} else {
					play(currentPlayingMusicPosition);
				}
				break;
			default:
				break;
			}
		}

		/**
		 * 前一首
		 */
		public void previous() {
			if (currentPlayingMusicPosition == 0) {
				return;
			}
			currentPlayingMusicPosition--;
			play(currentPlayingMusicPosition);
		}

		/**
		 * 有人改变模式了，我得把它记下来
		 */
		public void setPlayMode(int mode) {
			currentMode = mode;
		}

		/**
		 * 告诉别人，你现在到底是顺序播放，还是随机乱弹
		 * 
		 * @MODE_ONE_LOOP = 1;
		 * @MODE_ALL_LOOP = 2;
		 * @MODE_RANDOM= 3;
		 * @MODE_SEQUENCE = 4;
		 * 
		 * @return
		 */
		public int getPlayMode() {
			return currentMode;
		}

		/**
		 * @STATE_PLAYING = 5;
		 * @STATE_PAUSE = 6;
		 * @STATE_STOP = 7;
		 * 
		 * @return
		 */
		public int getState() {
			return currentState;
		}

		public HashMap<String, Object> getCurrentPlayingMusic() {
			return currentPlayingMusic;
		}

		public MediaPlayer getPlayer() {
			return player;
		}

		public void setOnPlayStateChangeListener(OnPlayStateChangeListener I) {
			onPlayStateChangeListener = I;
		}
	}

}
