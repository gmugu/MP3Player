package com.mugu.mp3prayer.fragment;

import com.mugu.mp3prayer.R;
import com.mugu.mp3prayer.utils.TabsFragmentPagerAdapter;

public class DownloadFragment extends BaseMainFragment {

	public static DownloadFragment newInstance() {
		return new DownloadFragment();
	}

	ChildOfDownload_Downloading f1 = new ChildOfDownload_Downloading();
	ChildOfDownload_DoneDownload f2 = new ChildOfDownload_DoneDownload();

	@Override
	protected void onSetupTabAdapter(TabsFragmentPagerAdapter adapter) {

		adapter.addTab(getResources().getString(R.string.child1_of_tab2),
				"ChildOfDownload_Downloading", f1, null);
		adapter.addTab(getResources().getString(R.string.child2_of_tab2),
				"ChildOfDownload_DoneDownload", f2, null);
	}

}
