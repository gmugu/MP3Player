package com.mugu.mp3prayer.net;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LyricManager extends BaseNetManager {

	private static final String UTF_8 = "utf-8";

	/*
	 * 获得歌词列表信息的Json
	 */
	private String getLrcListWithJson(String musicName, String singerName) {

		try {
			musicName = URLEncoder.encode(musicName, UTF_8);
			if (singerName != null) {
				singerName = URLEncoder.encode(singerName, UTF_8);
			}
		} catch (UnsupportedEncodingException e2) {
			e2.printStackTrace();
		}

		String strUrl = null;
		if (singerName == null) {
			strUrl = "http://tingapi.ting.baidu.com/v1/restserver/ting?from=android&version=4.9.2.0&method=baidu.ting.search.merge&format=json&query="
					+ musicName;
		} else {
			strUrl = "http://tingapi.ting.baidu.com/v1/restserver/ting?from=android&version=4.9.2.0&method=baidu.ting.search.merge&format=json&query="
					+ musicName + "-" + singerName;
		}

		return getStringByUrl(strUrl);
	}

	/*
	 * 从网络获取歌词列表
	 */
	public ArrayList<HashMap<String, String>> getLyrArrayList(String musicName) {

		return getLyrArrayList(musicName, null);
	}

	public ArrayList<HashMap<String, String>> getLyrArrayList(String musicName,
			String singerName) {

		String strJson = getLrcListWithJson(musicName, singerName);
		ArrayList<HashMap<String, String>> musicMsgArrayList = new ArrayList<HashMap<String, String>>();

		try {
			JSONObject root = new JSONObject(strJson);
			JSONArray rootArray = root.getJSONObject("result")
					.getJSONObject("song_info").getJSONArray("song_list");
			for (int i = 0; i < rootArray.length(); i++) {
				HashMap<String, String> tmp = new HashMap<String, String>();
				JSONObject musicTmp = rootArray.getJSONObject(i);
				// tmp.put("song_id", musicTmp.getString("song_id"));
				tmp.put("musicName", musicTmp.getString("title"));
				tmp.put("singerName", musicTmp.getString("author"));
				tmp.put("lrcLink", musicTmp.getString("lrclink"));
				musicMsgArrayList.add(tmp);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}

		if (musicMsgArrayList.size() == 0) {
			return getLyrArrayList(musicName, "");
		}

		return musicMsgArrayList;
	}

	/*
	 * 从本地获得歌词文本
	 */
	public String getLrcTextFromLocal(String musicName, String location) {

		StringWriter result = new StringWriter();
		File dirFile = new File(location);
		if (!dirFile.isDirectory()) {
			return null;
		}
		String lrcFilePath = dirFile.getAbsolutePath() + "/" + musicName
				+ ".lrc";

		try {
			BufferedReader br = new BufferedReader(new FileReader(lrcFilePath));
			PrintWriter pw = new PrintWriter(result);
			String tmp = null;
			while ((tmp = br.readLine()) != null) {
				pw.println(tmp);
			}
			br.close();

		} catch (FileNotFoundException e) {
			return null;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result.toString();
	}

	/*
	 * 从网络获得歌词文本并写入本地磁盘
	 */
	public String downloadLrc(String musicName, String lrclink,
			String toLocation) {

		// 检查传入的路径是否正确
		File dirFile = new File(toLocation);
		if (!dirFile.isDirectory()) {
			return null;
		}

		String lrcFilePath = dirFile.getAbsolutePath() + "/" + musicName
				+ ".lrc";
		String lrcText = getStringByUrl(lrclink);

		// 将歌词写入磁盘
		try {
			PrintWriter pw = new PrintWriter(new FileWriter(new File(
					lrcFilePath)));
			pw.print(lrcText);
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return lrcText;
	}

}
