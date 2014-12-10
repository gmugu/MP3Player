package com.mugu.mp3prayer.utils;

public class MusicInfo {

	private String musicName;
	private String singerName;
	private String srcPath;
	private String coverPath;
	private String lrcPath;

	public MusicInfo(String musicName, String singerName, String srcPath) {
		this.musicName = musicName;
		this.singerName = singerName;
		this.srcPath = srcPath;
	}

	public String getMusicName() {
		return musicName;
	}

	public void setMusicName(String musicName) {
		this.musicName = musicName;
	}

	public String getSingerName() {
		return singerName;
	}

	public void setSingerName(String singerName) {
		this.singerName = singerName;
	}

	public String getSrcPath() {
		return srcPath;
	}

	public void setSrcPath(String srcPath) {
		this.srcPath = srcPath;
	}

	public String getCoverPath() {
		return coverPath;
	}

	public void setCoverPath(String coverPath) {
		this.coverPath = coverPath;
	}

	public String getLrcPath() {
		return lrcPath;
	}

	public void setLrcPath(String lrcPath) {
		this.lrcPath = lrcPath;
	}

}
