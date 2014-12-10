package com.mugu.mp3prayer.utils;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * 每个tab的实体类，描述了每个tab的标题等等
 * 
 * @author shijunxing
 * @date Nov 25, 2014 9:04:11 PM
 * @version 1.0
 */
public final class TabInfo {
	public final String tag;
	public final Bundle args;
	public final String title;

	public final Fragment fragment;

	public TabInfo(String _title, String _tag, Fragment _fragment, Bundle _args) {
		title = _title;
		tag = _tag;
		args = _args;
		fragment = _fragment;
	}
}
