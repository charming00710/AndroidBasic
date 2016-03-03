package com.example.beeplayer.bs.json;

import java.util.List;

public class FindWeek {

	private int pageCount;

	private int totalCount;

	private List<Data> data;

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getPageCount() {
		return this.pageCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getTotalCount() {
		return this.totalCount;
	}

	public void setData(List<Data> data) {
		this.data = data;
	}

	public List<Data> getData() {
		return this.data;
	}

	public class Data {
		private int albumId;

		private String name;

		private String description;

		private String singerName;

		private String picUrl;

		private int publishYear;

		private String publishDate;

		private String lang;

		private String[] songs;

		private AlbumRightKey albumRightKey;

		private int status;

		private int isExclusive;

		private int id;

		private int week;

		private int year;

		public void setAlbumId(int albumId) {
			this.albumId = albumId;
		}

		public int getAlbumId() {
			return this.albumId;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getName() {
			return this.name;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getDescription() {
			return this.description;
		}

		public void setSingerName(String singerName) {
			this.singerName = singerName;
		}

		public String getSingerName() {
			return this.singerName;
		}

		public void setPicUrl(String picUrl) {
			this.picUrl = picUrl;
		}

		public String getPicUrl() {
			return this.picUrl;
		}

		public void setPublishYear(int publishYear) {
			this.publishYear = publishYear;
		}

		public int getPublishYear() {
			return this.publishYear;
		}

		public void setPublishDate(String publishDate) {
			this.publishDate = publishDate;
		}

		public String getPublishDate() {
			return this.publishDate;
		}

		public void setLang(String lang) {
			this.lang = lang;
		}

		public String getLang() {
			return this.lang;
		}

		public void setSongs(String[] songs) {
			this.songs = songs;
		}

		public String[] getSongs() {
			return this.songs;
		}

		public void setAlbumRightKey(AlbumRightKey albumRightKey) {
			this.albumRightKey = albumRightKey;
		}

		public AlbumRightKey getAlbumRightKey() {
			return this.albumRightKey;
		}

		public void setStatus(int status) {
			this.status = status;
		}

		public int getStatus() {
			return this.status;
		}

		public void setIsExclusive(int isExclusive) {
			this.isExclusive = isExclusive;
		}

		public int getIsExclusive() {
			return this.isExclusive;
		}

		public void setId(int id) {
			this.id = id;
		}

		public int getId() {
			return this.id;
		}

		public void setWeek(int week) {
			this.week = week;
		}

		public int getWeek() {
			return this.week;
		}

		public void setYear(int year) {
			this.year = year;
		}

		public int getYear() {
			return this.year;
		}

	}
	public class AlbumRightKey {
		private boolean buyFlag;

		private int price;

		private int count;

		private boolean buy;

		private String dmsg;

		public void setBuyFlag(boolean buyFlag) {
			this.buyFlag = buyFlag;
		}

		public boolean getBuyFlag() {
			return this.buyFlag;
		}

		public void setPrice(int price) {
			this.price = price;
		}

		public int getPrice() {
			return this.price;
		}

		public void setCount(int count) {
			this.count = count;
		}

		public int getCount() {
			return this.count;
		}

		public void setBuy(boolean buy) {
			this.buy = buy;
		}

		public boolean getBuy() {
			return this.buy;
		}

		public void setDmsg(String dmsg) {
			this.dmsg = dmsg;
		}

		public String getDmsg() {
			return this.dmsg;
		}

	}
}
