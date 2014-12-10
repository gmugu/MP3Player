package com.mugu.mp3prayer.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SearchFragment extends Fragment {

	public static SearchFragment newInstance() {
		return new SearchFragment();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		TextView tv = new TextView(getActivity());
		tv.setText("搜索界面");
		tv.setGravity(Gravity.CENTER);
		tv.setTextSize(20);
		return tv;
	}
}
