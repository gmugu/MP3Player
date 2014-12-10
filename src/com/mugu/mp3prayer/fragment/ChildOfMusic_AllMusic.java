package com.mugu.mp3prayer.fragment;

import java.util.HashMap;

import com.mugu.mp3prayer.R;
import com.mugu.mp3prayer.utils.DataUtils;
import com.mugu.mp3prayer.utils.MyApplication;

import android.R.integer;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ChildOfMusic_AllMusic extends Fragment {

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
					MyApplication.allMusicList, R.layout.all_music_list, from,
					to);
			mView.setAdapter(adapter);
			mView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					LinearLayout ll = (LinearLayout) view;
					HashMap<String, Object> hashmap = new HashMap<String, Object>();
					String musicName = ((TextView) ll
							.findViewById(R.id.musicName)).getText().toString();
					String singerName = ((TextView) ll
							.findViewById(R.id.singerName)).getText()
							.toString();
					String srcPath = ((TextView) ll.findViewById(R.id.srcPath))
							.getText().toString();
					hashmap.put("musicName", musicName);
					hashmap.put("singerName", singerName);
					hashmap.put("srcPath", srcPath);
					MyApplication.playMusicList.add(hashmap);
					new Thread(new Runnable() {

						@Override
						public void run() {
							try {
								Thread.sleep(5000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							DataUtils.updateAllListSQLFromApp();
							Log.d(TAG, "finish");
						}
					}).start();
				}
			});
			return mView;
		}
	}
	// @Override
	// public void onAttach(Activity activity) {
	// // TODO Auto-generated method stub
	// super.onAttach(activity);
	// Log.d(TAG, "onAttach");
	// }
	//
	// @Override
	// public void onCreate(Bundle savedInstanceState) {
	// // TODO Auto-generated method stub
	// super.onCreate(savedInstanceState);
	// Log.d(TAG, "onCreate");
	// }
	//
	// @Override
	// public void onActivityCreated(Bundle savedInstanceState) {
	// // TODO Auto-generated method stub
	// super.onActivityCreated(savedInstanceState);
	// Log.d(TAG, "onActivityCreated");
	//
	// }
	//
	// @Override
	// public void onStart() {
	// // TODO Auto-generated method stub
	// super.onStart();
	// Log.d(TAG, "onStart");
	// }
	//
	// @Override
	// public void onResume() {
	// // TODO Auto-generated method stub
	// super.onResume();
	// Log.d(TAG, "onResume");
	// }
	//
	// @Override
	// public void onPause() {
	// // TODO Auto-generated method stub
	// super.onPause();
	// Log.d(TAG, "onPause");
	// }
	//
	// @Override
	// public void onStop() {
	// // TODO Auto-generated method stub
	// super.onStop();
	// Log.d(TAG, "onStop");
	// }
	//
	// @Override
	// public void onDestroyView() {
	// // TODO Auto-generated method stub
	// super.onDestroyView();
	// Log.d(TAG, "onDestroyView");
	// }
	//
	// @Override
	// public void onDestroy() {
	// // TODO Auto-generated method stub
	// super.onDestroy();
	// Log.d(TAG, "onDestroy");
	// }
	//
	// @Override
	// public void onDetach() {
	// // TODO Auto-generated method stub
	// super.onDetach();
	// Log.d(TAG, "onDetach");
	// }

}
