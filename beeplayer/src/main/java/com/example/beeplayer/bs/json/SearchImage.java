package com.example.beeplayer.bs.json;

import java.util.List;

/**
 * ͼƬ����ʵ����
 * 
 * @author Administrator
 *
 */
public class SearchImage {

	private int code;

	private String msg;

	private int rows;

	private int pages;

	private List<Data> data;

	public void setCode(int code) {
		this.code = code;
	}

	public int getCode() {
		return this.code;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getMsg() {
		return this.msg;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getRows() {
		return this.rows;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public int getPages() {
		return this.pages;
	}

	public void setData(List<Data> data) {
		this.data = data;
	}

	public List<Data> getData() {
		return this.data;
	}

	public class Data {
		private int _id;

		private String name;

		private List<PicUrls> picUrls;

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

		public void setPicUrls(List<PicUrls> picUrls) {
			this.picUrls = picUrls;
		}

		public List<PicUrls> getPicUrls() {
			return this.picUrls;
		}

		public class PicUrls {
			private int _id;

			private int width;

			private int height;

			private int refId;

			private int quality;

			private String picUrl;

			public void set_id(int _id) {
				this._id = _id;
			}

			public int get_id() {
				return this._id;
			}

			public void setWidth(int width) {
				this.width = width;
			}

			public int getWidth() {
				return this.width;
			}

			public void setHeight(int height) {
				this.height = height;
			}

			public int getHeight() {
				return this.height;
			}

			public void setRefId(int refId) {
				this.refId = refId;
			}

			public int getRefId() {
				return this.refId;
			}

			public void setQuality(int quality) {
				this.quality = quality;
			}

			public int getQuality() {
				return this.quality;
			}

			public void setPicUrl(String picUrl) {
				this.picUrl = picUrl;
			}

			public String getPicUrl() {
				return this.picUrl;
			}

		}
	}

}