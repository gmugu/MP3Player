package com.mugu.mp3prayer.activity;

import com.mugu.mp3prayer.R;
import com.mugu.mp3prayer.utils.DataUtils;
import com.mugu.mp3prayer.utils.MyApplication;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

public class StartActivity extends Activity {

	public static Context context;
	private ContentResolver resolver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.start_layout);
		context = this;
		resolver = getContentResolver();

		new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... params) {
				SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
						context.getFilesDir() + "/music_list.db3", null);
				db.execSQL("create table if not exists all_music (id integer primary key autoincrement not null, musicName varchar(50) , srcPath varchar(100) , singerName varchar(50))");
				db.execSQL("create table if not exists play_music (id integer primary key autoincrement not null, musicName varchar(50) , srcPath varchar(100) , singerName varchar(50))");
				db.execSQL("create table if not exists love_music (id integer primary key autoincrement not null, musicName varchar(50) , srcPath varchar(100) , singerName varchar(50), style varchar(50))");
				db.close();

				DataUtils.getAllMusicListFromSQL();
				if (MyApplication.allMusicList.isEmpty()) {
					DataUtils.updateAllMusicDataFromSystem(resolver);
				}
				DataUtils.updateAppAllListsFromSQL();

				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				startActivity(new Intent(context, MainActivity.class));
				finish();
				super.onPostExecute(result);
			}

		}.execute();

	}
}