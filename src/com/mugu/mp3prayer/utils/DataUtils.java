package com.mugu.mp3prayer.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import com.mugu.mp3prayer.activity.StartActivity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.MediaStore;

public class DataUtils {

	private static Context context = StartActivity.context;

	/*
	 * 更新所有歌曲数据
	 */
	public static void updateAllMusicDataFromSystem(ContentResolver resolver) {
		MyApplication.allMusicList.clear();
		// 打开本应用歌曲列表的数据库
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				context.getFilesDir() + "/music_list.db3", null);
		db.execSQL("delete from all_music");

		// 搜索本地歌曲
		Cursor resolverCursor = resolver.query(
				MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null,
				MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
		while (resolverCursor.moveToNext()) {
			// 获得歌曲信息
			HashMap<String, Object> hashMap = new HashMap<String, Object>();
			String musicName = resolverCursor.getString(resolverCursor
					.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
			String singerName = resolverCursor.getString(resolverCursor
					.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
			String srcPath = resolverCursor.getString(resolverCursor
					.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));

			// 将歌曲信息存入allMusicList
			hashMap.put("musicName", musicName);
			hashMap.put("singerName", singerName);
			hashMap.put("srcPath", srcPath);
			MyApplication.allMusicList.add(hashMap);

			// 将歌曲信息存入数据库
			ContentValues values = new ContentValues();
			values.put("musicName", musicName);
			values.put("singerName", singerName);
			values.put("srcPath", srcPath);
			db.insert("all_music", null, values);
		}
		db.close();

	}

	private static ArrayList<HashMap<String, Object>> readListFromSQL(
			String listName) {

		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				context.getFilesDir() + "/music_list.db3", null);
		Cursor cursor = db.rawQuery("select * from " + listName, null);
		ArrayList<HashMap<String, Object>> tmp = new ArrayList<HashMap<String, Object>>();
		while (cursor.moveToNext()) {
			String musicName = cursor.getString(cursor
					.getColumnIndexOrThrow("musicName"));
			String singerName = cursor.getString(cursor
					.getColumnIndexOrThrow("singerName"));
			String srcPath = cursor.getString(cursor
					.getColumnIndexOrThrow("srcPath"));

			// 将歌曲信息存入allMusicList
			HashMap<String, Object> hashMap = new HashMap<String, Object>();
			hashMap.put("musicName", musicName);
			hashMap.put("singerName", singerName);
			hashMap.put("srcPath", srcPath);
			tmp.add(hashMap);
		}
		cursor.close();
		db.close();
		return tmp;

	}

	public static void getAllMusicListFromSQL() {
		MyApplication.allMusicList = readListFromSQL("all_music");
	}

	public static void getPlayMusicListFromSQL() {
		MyApplication.playMusicList = readListFromSQL("play_music");
	}

	public static void getLoveMusicListFromSQL() {
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				context.getFilesDir() + "/music_list.db3", null);
		Cursor cursor = db.rawQuery("select * from love_music", null);
		while (cursor.moveToNext()) {
			String musicName = cursor.getString(cursor
					.getColumnIndexOrThrow("musicName"));
			String singerName = cursor.getString(cursor
					.getColumnIndexOrThrow("singerName"));
			String srcPath = cursor.getString(cursor
					.getColumnIndexOrThrow("srcPath"));
			String style = cursor.getString(cursor
					.getColumnIndexOrThrow("style"));
			// 将歌曲信息存入allMusicList
			HashMap<String, Object> hashMapTmp = new HashMap<String, Object>();
			hashMapTmp.put("musicName", musicName);
			hashMapTmp.put("singerName", singerName);
			hashMapTmp.put("srcPath", srcPath);

			if (!MyApplication.loveMusicList.containsKey(style)) {
				ArrayList<HashMap<String, Object>> ArrayListTmp = new ArrayList<HashMap<String, Object>>();
				ArrayListTmp.add(hashMapTmp);
				MyApplication.loveMusicList.put(style, ArrayListTmp);
			} else {
				MyApplication.loveMusicList.get(style).add(hashMapTmp);
			}
		}
	}

	public static void updateAppAllListsFromSQL() {
		getAllMusicListFromSQL();
		getPlayMusicListFromSQL();
		getLoveMusicListFromSQL();
	}

	/*
	 * 更新数据库的播放列表
	 */
	public static void updatePalyMusicSQLFromApp() {
		// 打开数据库
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				context.getFilesDir() + "/music_list.db3", null);
		// 更新播放列表
		db.execSQL("delete from play_music");
		db.execSQL("update sqlite_sequence SET seq = 0 where name = 'play_music'");
		for (Iterator<HashMap<String, Object>> iterator = MyApplication.playMusicList
				.iterator(); iterator.hasNext();) {
			HashMap<String, Object> hashMap = (HashMap<String, Object>) iterator
					.next();
			ContentValues values = new ContentValues();
			values.put("musicName", (String) hashMap.get("musicName"));
			values.put("singerName", (String) hashMap.get("singerName"));
			values.put("srcPath", (String) hashMap.get("srcPath"));
			db.insert("play_music", null, values);
		}
		db.close();
	}

	/*
	 * 更新数据库的喜欢列表
	 */
	public static void updateLoveMusicSQLFromApp() {
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				context.getFilesDir() + "/music_list.db3", null);
		db.execSQL("delete from love_music");
		db.execSQL("update sqlite_sequence SET seq = 0 where name = 'love_music'");

		Iterator<Entry<String, ArrayList<HashMap<String, Object>>>> it = MyApplication.loveMusicList
				.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, ArrayList<HashMap<String, Object>>> entry = (Entry<String, ArrayList<HashMap<String, Object>>>) it
					.next();
			ArrayList<HashMap<String, Object>> arrayList = entry.getValue();
			String style = entry.getKey();
			Iterator<HashMap<String, Object>> i = arrayList.iterator();
			while (i.hasNext()) {

				ContentValues values = new ContentValues();
				values.put("musicName", (String) i.next().get("musicName"));
				values.put("singerName", (String) i.next().get("singerName"));
				values.put("srcPath", (String) i.next().get("srcPath"));
				values.put("style", style);
				db.insert("love_music", null, values);
			}
		}
		db.close();
	}

	public static void updateAllListSQLFromApp() {
		updateLoveMusicSQLFromApp();
		updatePalyMusicSQLFromApp();
	}
}
