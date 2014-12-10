package com.mugu.mp3prayer.fragment;

import com.mugu.mp3prayer.R;
import com.mugu.mp3prayer.utils.TabsFragmentPagerAdapter;

public class MusicFragment extends BaseMainFragment {

	public static MusicFragment newInstance() {
		return new MusicFragment();
	}

	ChildOfMusic_AllMusic f1 = new ChildOfMusic_AllMusic();
	ChildOfMusic_LoveMusic f2 = new ChildOfMusic_LoveMusic();
	ChildOfMusic_PlayingList f3 = new ChildOfMusic_PlayingList();

	@Override
	protected void onSetupTabAdapter(TabsFragmentPagerAdapter adapter) {

		adapter.addTab(getResources().getString(R.string.child1_of_tab1),
				"ChildOfMusic_AllMusic", f1, null);
		adapter.addTab(getResources().getString(R.string.child2_of_tab1),
				"ChildOfMusic_LoveMusic", f2, null);
		adapter.addTab(getResources().getString(R.string.child3_of_tab1),
				"ChildOfMusic_PlayingList", f3, null);

	}

}
