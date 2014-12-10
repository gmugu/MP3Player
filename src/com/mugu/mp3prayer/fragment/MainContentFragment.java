package com.mugu.mp3prayer.fragment;

import com.mugu.mp3prayer.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainContentFragment extends Fragment implements OnClickListener {

	private final String TAG = getClass().getSimpleName();

	private View root;
	private RelativeLayout playBar;
	private RelativeLayout musicCase;
	private RelativeLayout downloadCase;
	private RelativeLayout searchCase;
	private Fragment musicFragment;
	private Fragment downloadFragment;
	private Fragment searchFragment;

	private int imageOnNormalId[] = { R.drawable.music_case,
			R.drawable.download_case, R.drawable.search_case };
	private int imageOnSelectedId[] = { R.drawable.music_case_on_selected,
			R.drawable.download_case_on_selected,
			R.drawable.search_case_on_selected };
	private int viewIdOfTab[][] = { { R.id.iv1, R.id.tv1 },
			{ R.id.iv2, R.id.tv2 }, { R.id.iv3, R.id.tv3 } };

	private int previousCase;
	private Fragment previousFragment;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		root = inflater.inflate(R.layout.main_content_fragment, null);
		if (savedInstanceState == null) {
			if (musicFragment == null) {
				musicFragment = MusicFragment.newInstance();
			}
			getChildFragmentManager().beginTransaction()
					.add(R.id.main_content, musicFragment).commit();
			previousFragment = musicFragment;
			previousCase = 0;
			updateTabView(0);
		}
		musicCase = (RelativeLayout) root.findViewById(R.id.music);
		downloadCase = (RelativeLayout) root.findViewById(R.id.download);
		searchCase = (RelativeLayout) root.findViewById(R.id.search);
		musicCase.setOnClickListener(this);
		downloadCase.setOnClickListener(this);
		searchCase.setOnClickListener(this);

		playBar = (RelativeLayout) root.findViewById(R.id.play_bar);
		playBar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				FragmentTransaction ft = getFragmentManager()
						.beginTransaction();
				ft.addToBackStack(null);
				ft.add(R.id.container, new test()).commit();

			}
		});
		return root;
	}

	static class test extends Fragment {
		String TAG = "test";

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			LinearLayout ll = new LinearLayout(getActivity());
			TextView tv = new TextView(getActivity());
			tv.setText("≤•∑≈ΩÁ√Ê");
			tv.setTextSize(40);
			ll.setGravity(Gravity.CENTER);
			ll.addView(tv);
			ll.setBackgroundColor(0xff11ff00);
			ll.setOnTouchListener(new OnTouchListener() {
				float previousX;
				float previousY;

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						Log.d(TAG, "ACTION_DOWN " + MotionEvent.ACTION_DOWN);
						previousX = event.getX();
						previousY = event.getY();
						Log.d(TAG, previousX + " " + previousY);
						break;
					case MotionEvent.ACTION_MOVE:
						float currentX = event.getX();
						float currentY = event.getY();
						float viewCurX = v.getX();
						float viewCurY = v.getY();
						viewCurX += (currentX - previousX);
						viewCurY += (currentY - previousY);
						v.setX(viewCurX);
						v.setY(viewCurY);
						previousX = currentX;
						previousY = currentY;
						break;
					case MotionEvent.ACTION_UP:
						Log.d(TAG, "ACTION_UP " + MotionEvent.ACTION_UP);

						break;

					default:
						break;
					}
					return true;
				}
			});
			return ll;
		}

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.music:
			if (previousCase == 0) {
				break;
			}
			if (musicFragment == null) {
				musicFragment = MusicFragment.newInstance();
			}
			switchContent(previousFragment, musicFragment);
			updateTabView(0);
			previousFragment = musicFragment;
			break;
		case R.id.download:
			if (previousCase == 1) {
				break;
			}
			if (downloadFragment == null) {
				downloadFragment = DownloadFragment.newInstance();
			}
			switchContent(previousFragment, downloadFragment);
			updateTabView(1);
			previousFragment = downloadFragment;
			break;
		case R.id.search:
			if (previousCase == 2) {
				break;
			}
			if (searchFragment == null) {
				searchFragment = SearchFragment.newInstance();
			}
			switchContent(previousFragment, searchFragment);
			updateTabView(2);
			previousFragment = searchFragment;
			break;

		default:
			break;
		}
	}

	private void switchContent(Fragment from, Fragment to) {
		FragmentTransaction ft = getChildFragmentManager().beginTransaction();
		if (!to.isAdded()) {
			ft.hide(from).add(R.id.main_content, to).commit();
		} else {
			ft.hide(from).show(to).commit();
		}
	}

	private void updateTabView(int which) {

		ImageView curIv = (ImageView) root.findViewById(viewIdOfTab[which][0]);
		ImageView preIv = (ImageView) root
				.findViewById(viewIdOfTab[previousCase][0]);

		TextView curTv = (TextView) root.findViewById(viewIdOfTab[which][1]);
		TextView preTv = (TextView) root
				.findViewById(viewIdOfTab[previousCase][1]);

		preIv.setImageResource(imageOnNormalId[previousCase]);
		curIv.setImageResource(imageOnSelectedId[which]);

		preTv.setTextColor(getResources().getColor(R.color.root_tab_text_color));
		curTv.setTextColor(getResources().getColor(
				R.color.root_tab_text_color_on_selected));

		previousCase = which;
	}
}
