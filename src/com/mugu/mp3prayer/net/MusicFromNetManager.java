package com.mugu.mp3prayer.net;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class MusicFromNetManager extends BaseNetManager {

	private static final String UTF_8 = "utf-8";
	private String xiamiVar_xiamitoken;

	private String getXiamiMusicListWithJson(String key, String _xiamitoken) {

		try {
			key = URLEncoder.encode(key, UTF_8);
		} catch (UnsupportedEncodingException e2) {
			e2.printStackTrace();
		}
		String strUrl = "http://www.xiami.com/web/search-songs?key=" + key
				+ "&_xiamitoken=" + _xiamitoken;

		return getStringByUrl(strUrl);

	}

	/*
	 * 从网络获取虾米网请求码
	 */
	private String getXiamiVar_xiamitokenByNet() throws MalformedURLException,
			IOException {
		Document content = null;
		content = Jsoup.parse(new URL("http://m.xiami.com/"), 5000);
		String resultStr = content.getElementsByTag("head").first()
				.getElementsByTag("script").first().childNode(0).toString();
		resultStr = resultStr.substring(resultStr.indexOf("\'") + 1);
		resultStr = resultStr.substring(0, resultStr.indexOf("\'"));
		xiamiVar_xiamitoken = resultStr;
		return resultStr;

	}

	/*
	 * 通过关键字获取歌曲信息
	 */
	public ArrayList<HashMap<String, String>> searchMusicFromNet(String keyword) {

		ArrayList<HashMap<String, String>> musicMsgArrayList = new ArrayList<HashMap<String, String>>();
		if (xiamiVar_xiamitoken == null) {
			try {
				getXiamiVar_xiamitokenByNet();
			} catch (MalformedURLException e) {
				e.printStackTrace();
				return null;
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}

		String strJson = getXiamiMusicListWithJson(keyword, xiamiVar_xiamitoken);

		try {
			JSONArray rootArray = new JSONArray(strJson);
			for (int i = 0; i < rootArray.length(); i++) {
				HashMap<String, String> tmp = new HashMap<String, String>();
				JSONObject rootMusicTmp = rootArray.getJSONObject(i);
				// tmp.put("id", rootMusicTmp.getString("id"));
				tmp.put("musicName", rootMusicTmp.getString("title"));
				tmp.put("singerName", rootMusicTmp.getString("author"));
				tmp.put("coverLink", rootMusicTmp.getString("cover"));
				tmp.put("srcLink", rootMusicTmp.getString("src"));
				musicMsgArrayList.add(tmp);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return musicMsgArrayList;

	}
}
