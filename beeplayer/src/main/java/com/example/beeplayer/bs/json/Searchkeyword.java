package com.example.beeplayer.bs.json;

import java.util.List;
/**
 * ������ؼ�����ʾʵ����
 * @author Administrator
 *
 */
public class Searchkeyword {

	private int code;

	private Data data;

	public void setCode(int code) {
		this.code = code;
	}

	public int getCode() {
		return this.code;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public Data getData() {
		return this.data;
	}

	public class Data {
		private List<Song> song;

		private List<Singer> singer;

		private List<Keyword> keyword;

		private String order;

		public void setSong(List<Song> song) {
			this.song = song;
		}

		public List<Song> getSong() {
			return this.song;
		}

		public void setSinger(List<Singer> singer) {
			this.singer = singer;
		}

		public List<Singer> getSinger() {
			return this.singer;
		}

		public void setKeyword(List<Keyword> keyword) {
			this.keyword = keyword;
		}

		public List<Keyword> getKeyword() {
			return this.keyword;
		}

		public void setOrder(String order) {
			this.order = order;
		}

		public String getOrder() {
			return this.order;
		}

		public class Song {
			private int _id;

			private String name;

			private String singer_name;

			private double weight;

			public void set_id(int _id) {
				this._id = _id;
			}

			public int get_id() {
				return this._id;
			}

			public void setName(String name) {
				this.name = name;
			}

			public String getName() {
				return this.name;
			}

			public void setSinger_name(String singer_name) {
				this.singer_name = singer_name;
			}

			public String getSinger_name() {
				return this.singer_name;
			}

			public void setWeight(double weight) {
				this.weight = weight;
			}

			public double getWeight() {
				return this.weight;
			}

		}

		public class Singer {
			private int _id;

			private String alias_name;

			private String singer_name;

			private int song_num;

			private int album_num;

			private double weight;

			public void set_id(int _id) {
				this._id = _id;
			}

			public int get_id() {
				return this._id;
			}

			public void setAlias_name(String alias_name) {
				this.alias_name = alias_name;
			}

			public String getAlias_name() {
				return this.alias_name;
			}

			public void setSinger_name(String singer_name) {
				this.singer_name = singer_name;
			}

			public String getSinger_name() {
				return this.singer_name;
			}

			public void setSong_num(int song_num) {
				this.song_num = song_num;
			}

			public int getSong_num() {
				return this.song_num;
			}

			public void setAlbum_num(int album_num) {
				this.album_num = album_num;
			}

			public int getAlbum_num() {
				return this.album_num;
			}

			public void setWeight(double weight) {
				this.weight = weight;
			}

			public double getWeight() {
				return this.weight;
			}

		}

		public class Keyword {
			private int hit;

			private String val;

			private int hot;

			public void setHit(int hit) {
				this.hit = hit;
			}

			public int getHit() {
				return this.hit;
			}

			public void setVal(String val) {
				this.val = val;
			}

			public String getVal() {
				return this.val;
			}

			public void setHot(int hot) {
				this.hot = hot;
			}

			public int getHot() {
				return this.hot;
			}
		}
	}
}
