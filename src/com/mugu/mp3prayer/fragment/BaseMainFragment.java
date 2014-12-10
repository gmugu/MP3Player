package com.mugu.mp3prayer.fragment;

import java.lang.reflect.Field;

import com.mugu.mp3prayer.R;
import com.mugu.mp3prayer.utils.PagerSlidingTabStrip;
import com.mugu.mp3prayer.utils.TabsFragmentPagerAdapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 主界面的基类，有导航的tab
 * 
 * @author shijunxing
 * @date Nov 25, 2014 8:00:45 PM
 * @version 1.0
 */
public abstract class BaseMainFragment extends Fragment {

	protected PagerSlidingTabStrip mTabStrip;
	protected ViewPager mViewPager;
	protected TabsFragmentPagerAdapter mTabsAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.content_view, null);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		DisplayMetrics dm = getResources().getDisplayMetrics();
		mTabStrip = (PagerSlidingTabStrip) view.findViewById(R.id.tabstrip);
		mTabStrip.setIndicatorColorResource(R.color.tab_strip_indicator_color);
		mTabStrip.setBackgroundColor(getResources().getColor(
				R.color.tab_strip_background_color));
		mTabStrip.setTextColorResource(R.color.tab_strip_text_color);
		mTabStrip.setTextSize((int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_SP, 14, dm));
		mTabStrip.setIndicatorHeight((int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 3, dm));
		mTabStrip.setTextColorOnSelected(getResources().getColor(
				R.color.tab_strip_text_color_on_selected));
		mTabStrip.setShouldExpand(true);
		mViewPager = (ViewPager) view.findViewById(R.id.viewpager);

		mTabsAdapter = new TabsFragmentPagerAdapter(getChildFragmentManager(),
				mTabStrip, mViewPager);

		onSetupTabAdapter(mTabsAdapter);
		mTabsAdapter.notifyDataSetChanged();

		if (savedInstanceState != null) {
			int pos = savedInstanceState.getInt("position");
			mViewPager.setCurrentItem(pos, false);
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("position", mViewPager.getCurrentItem());
	}

	// @Override
	// public void onDetach() {
	// super.onDetach();
	// try {
	// Field childFragmentManager = Fragment.class
	// .getDeclaredField("mChildFragmentManager");
	// childFragmentManager.setAccessible(true);
	// childFragmentManager.set(this, null);
	//
	// } catch (NoSuchFieldException e) {
	// throw new RuntimeException(e);
	// } catch (IllegalAccessException e) {
	// throw new RuntimeException(e);
	// }
	// }

	/**
	 * 让子类去实现
	 * 
	 * @date Nov 25, 2014 8:01:26 PM
	 * @version 1.0
	 * @param adapter
	 */
	protected abstract void onSetupTabAdapter(TabsFragmentPagerAdapter adapter);
}