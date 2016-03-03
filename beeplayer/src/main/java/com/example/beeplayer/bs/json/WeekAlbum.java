package com.example.beeplayer.bs.json;

import java.util.List;

public class WeekAlbum {
	private int code;

	private String msg;

	private Data data;

	private int pageCount;

	private int totalCount;

	private int page;

	private int size;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public class Data {
		private int albumId;

		private String name;

		private String alias;

		private String description;

		private int type;

		private String typeName;

		private int coverId;

		private String picUrl;

		private int singerId;

		private String singerName;

		private int singerSFlag;

		private int publishYear;

		private String publishDate;

		private int publisher;

		private int companyId;

		private String companyName;

		private String lang;

		private List<SongList> songList;

		private String[] titleSongs;

		public int getAlbumId() {
			return albumId;
		}

		public void setAlbumId(int albumId) {
			this.albumId = albumId;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getAlias() {
			return alias;
		}

		public void setAlias(String alias) {
			this.alias = alias;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public int getType() {
			return type;
		}

		public void setType(int type) {
			this.type = type;
		}

		public String getTypeName() {
			return typeName;
		}

		public void setTypeName(String typeName) {
			this.typeName = typeName;
		}

		public int getCoverId() {
			return coverId;
		}

		public void setCoverId(int coverId) {
			this.coverId = coverId;
		}

		public String getPicUrl() {
			return picUrl;
		}

		public void setPicUrl(String picUrl) {
			this.picUrl = picUrl;
		}

		public int getSingerId() {
			return singerId;
		}

		public void setSingerId(int singerId) {
			this.singerId = singerId;
		}

		public String getSingerName() {
			return singerName;
		}

		public void setSingerName(String singerName) {
			this.singerName = singerName;
		}

		public int getSingerSFlag() {
			return singerSFlag;
		}

		public void setSingerSFlag(int singerSFlag) {
			this.singerSFlag = singerSFlag;
		}

		public int getPublishYear() {
			return publishYear;
		}

		public void setPublishYear(int publishYear) {
			this.publishYear = publishYear;
		}

		public String getPublishDate() {
			return publishDate;
		}

		public void setPublishDate(String publishDate) {
			this.publishDate = publishDate;
		}

		public int getPublisher() {
			return publisher;
		}

		public void setPublisher(int publisher) {
			this.publisher = publisher;
		}

		public int getCompanyId() {
			return companyId;
		}

		public void setCompanyId(int companyId) {
			this.companyId = companyId;
		}

		public String getCompanyName() {
			return companyName;
		}

		public void setCompanyName(String companyName) {
			this.companyName = companyName;
		}

		public String getLang() {
			return lang;
		}

		public void setLang(String lang) {
			this.lang = lang;
		}

		public List<SongList> getSongList() {
			return songList;
		}

		public void setSongList(List<SongList> songList) {
			this.songList = songList;
		}

		public String[] getTitleSongs() {
			return titleSongs;
		}

		public void setTitleSongs(String[] titleSongs) {
			this.titleSongs = titleSongs;
		}

		public class SongList {
			private int songId;

			private String name;

			private int singerId;

			private String singerName;

			private int singerSFlag;

			private int albumId;

			private String albumName;

			private List<AuditionList> auditionList;

			public int getSongId() {
				return songId;
			}

			public void setSongId(int songId) {
				this.songId = songId;
			}

			public String getName() {
				return name;
			}

			public void setName(String name) {
				this.name = name;
			}

			public int getSingerId() {
				return singerId;
			}

			public void setSingerId(int singerId) {
				this.singerId = singerId;
			}

			public String getSingerName() {
				return singerName;
			}

			public void setSingerName(String singerName) {
				this.singerName = singerName;
			}

			public int getSingerSFlag() {
				return singerSFlag;
			}

			public void setSingerSFlag(int singerSFlag) {
				this.singerSFlag = singerSFlag;
			}

			public int getAlbumId() {
				return albumId;
			}

			public void setAlbumId(int albumId) {
				this.albumId = albumId;
			}

			public String getAlbumName() {
				return albumName;
			}

			public void setAlbumName(String albumName) {
				this.albumName = albumName;
			}

			public List<AuditionList> getAuditionList() {
				return auditionList;
			}

			public void setAuditionList(List<AuditionList> auditionList) {
				this.auditionList = auditionList;
			}

			public class AuditionList {
				private int bitRate;

				private int duration;

				private int size;

				private String suffix;

				private String url;

				private String typeDescription;

				public void setBitRate(int bitRate) {
					this.bitRate = bitRate;
				}

				public int getBitRate() {
					return this.bitRate;
				}

				public void setDuration(int duration) {
					this.duration = duration;
				}

				public int getDuration() {
					return this.duration;
				}

				public void setSize(int size) {
					this.size = size;
				}

				public int getSize() {
					return this.size;
				}

				public void setSuffix(String suffix) {
					this.suffix = suffix;
				}

				public String getSuffix() {
					return this.suffix;
				}

				public void setUrl(String url) {
					this.url = url;
				}

				public String getUrl() {
					return this.url;
				}

				public void setTypeDescription(String typeDescription) {
					this.typeDescription = typeDescription;
				}

				public String getTypeDescription() {
					return this.typeDescription;
				}

			}

		}

	}
}
