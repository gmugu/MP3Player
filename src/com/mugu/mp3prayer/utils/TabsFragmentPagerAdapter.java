package com.mugu.mp3prayer.utils;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.TabHost;

/**
 * tab适配器
 * 
 * @author shijunxing
 * @date Nov 25, 2014 9:09:29 PM
 * @version 1.0
 */
public class TabsFragmentPagerAdapter extends FragmentPagerAdapter implements
		ViewPager.OnPageChangeListener {

	private final Context mContext;
	private final PagerSlidingTabStrip mTabStrip;
	private final ViewPager mViewPager;
	private final ArrayList<TabInfo> mTabs = new ArrayList<TabInfo>();

	static class DummyTabFactory implements TabHost.TabContentFactory {
		private final Context mContext;

		public DummyTabFactory(Context context) {
			mContext = context;
		}

		@Override
		public View createTabContent(String tag) {
			View v = new View(mContext);
			v.setMinimumWidth(0);
			v.setMinimumHeight(0);
			return v;
		}
	}

	public TabsFragmentPagerAdapter(FragmentManager fm,
			PagerSlidingTabStrip tabStrip, ViewPager pager) {
		super(fm);
		mContext = pager.getContext();
		mTabStrip = tabStrip;
		mViewPager = pager;
		mViewPager.setAdapter(this);

		mViewPager.setOnPageChangeListener(this);

		mTabStrip.setViewPager(mViewPager, false);
	}

	public void addTab(String title, String tag, Fragment fragment, Bundle args) {
		TabInfo info = new TabInfo(title, tag, fragment, args);
		mTabs.add(info);
	}

	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
		mTabStrip.notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return mTabs.size();
	}

	@Override
	public Fragment getItem(int position) {
		TabInfo info = mTabs.get(position);
		return info.fragment;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return mTabs.get(position).title;
	}

	@Override
	public void onPageScrollStateChanged(int state) {
		mTabStrip.onPageScrollStateChanged(state);
	}

	@Override
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels) {
		mTabStrip
				.onPageScrolled(position, positionOffset, positionOffsetPixels);
	}

	@Override
	public void onPageSelected(int position) {
		mTabStrip.onPageSelected(position);
	}
}