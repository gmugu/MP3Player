package com.mugu.mp3prayer.fragment;

import com.mugu.mp3prayer.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ChildOfDownload_Downloading extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		TextView tv = new TextView(getActivity());
		tv.setText("Downloading List");
		tv.setGravity(Gravity.CENTER);
		tv.setTextSize(20);
		return tv;
	}
}
