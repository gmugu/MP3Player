package com.mugu.mp3prayer.fragment;

import com.mugu.mp3prayer.R;
import com.mugu.mp3prayer.utils.DataUtils;
import com.mugu.mp3prayer.utils.MyApplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class ChildOfMusic_PlayingList extends Fragment {

	private final String TAG = getClass().getSimpleName();
	ListView mView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		if (mView != null) {
			ViewGroup parent = (ViewGroup) mView.getParent();
			if (parent != null) {
				parent.removeView(mView);
			}
			return mView;
		} else {
			mView = new ListView(getActivity());
			String[] from = { "musicName", "singerName", "srcPath" };
			int[] to = { R.id.musicName, R.id.singerName, R.id.srcPath };
			SimpleAdapter adapter = new SimpleAdapter(getActivity(),
					MyApplication.playMusicList, R.layout.all_music_list, from,
					to);
			mView.setAdapter(adapter);
			return mView;
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
}
