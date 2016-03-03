package com.example.beeplayer.bs.json;

import java.util.List;
/**
 * ��������ʵ����
 * @author Administrator
 *
 */
public class SearchSong {
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
		private int songId;

		private String name;

		private String alias;

		private String remarks;

		private boolean firstHit;

		private int librettistId;

		private int composerId;

		private int singerId;

		private String singerName;

		private int singerSFlag;

		private int albumId;

		private String albumName;

		private int favorites;

		private int originalId;

		private int type;

		private int releaseYear;

		private int producer;

		private int publisher;

		private int status;

		private int audit;

		private int lang;

		private List<AuditionList> auditionList;

		private List<UrlList> urlList;

		private List<LlList> llList;

		private List<MvList> mvList;

		private int mvPickCount;

		private int mvBulletCount;

		private int outFlag;

		private int commentCount;

		private int riskRank;

		private RightKey rightKey;

		private int operType;

		private String level;

		private int isExclusive;

		private String picUrl;

		private int listenCount;

		private List<Singers> singers;

		public void setSongId(int songId) {
			this.songId = songId;
		}

		public int getSongId() {
			return this.songId;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getName() {
			return this.name;
		}

		public void setAlias(String alias) {
			this.alias = alias;
		}

		public String getAlias() {
			return this.alias;
		}

		public void setRemarks(String remarks) {
			this.remarks = remarks;
		}

		public String getRemarks() {
			return this.remarks;
		}

		public void setFirstHit(boolean firstHit) {
			this.firstHit = firstHit;
		}

		public boolean getFirstHit() {
			return this.firstHit;
		}

		public void setLibrettistId(int librettistId) {
			this.librettistId = librettistId;
		}

		public int getLibrettistId() {
			return this.librettistId;
		}

		public void setComposerId(int composerId) {
			this.composerId = composerId;
		}

		public int getComposerId() {
			return this.composerId;
		}

		public void setSingerId(int singerId) {
			this.singerId = singerId;
		}

		public int getSingerId() {
			return this.singerId;
		}

		public void setSingerName(String singerName) {
			this.singerName = singerName;
		}

		public String getSingerName() {
			return this.singerName;
		}

		public void setSingerSFlag(int singerSFlag) {
			this.singerSFlag = singerSFlag;
		}

		public int getSingerSFlag() {
			return this.singerSFlag;
		}

		public void setAlbumId(int albumId) {
			this.albumId = albumId;
		}

		public int getAlbumId() {
			return this.albumId;
		}

		public void setAlbumName(String albumName) {
			this.albumName = albumName;
		}

		public String getAlbumName() {
			return this.albumName;
		}

		public void setFavorites(int favorites) {
			this.favorites = favorites;
		}

		public int getFavorites() {
			return this.favorites;
		}

		public void setOriginalId(int originalId) {
			this.originalId = originalId;
		}

		public int getOriginalId() {
			return this.originalId;
		}

		public void setType(int type) {
			this.type = type;
		}

		public int getType() {
			return this.type;
		}

		public void setReleaseYear(int releaseYear) {
			this.releaseYear = releaseYear;
		}

		public int getReleaseYear() {
			return this.releaseYear;
		}

		public void setProducer(int producer) {
			this.producer = producer;
		}

		public int getProducer() {
			return this.producer;
		}

		public void setPublisher(int publisher) {
			this.publisher = publisher;
		}

		public int getPublisher() {
			return this.publisher;
		}

		public void setStatus(int status) {
			this.status = status;
		}

		public int getStatus() {
			return this.status;
		}

		public void setAudit(int audit) {
			this.audit = audit;
		}

		public int getAudit() {
			return this.audit;
		}

		public void setLang(int lang) {
			this.lang = lang;
		}

		public int getLang() {
			return this.lang;
		}

		public void setAuditionList(List<AuditionList> auditionList) {
			this.auditionList = auditionList;
		}

		public List<AuditionList> getAuditionList() {
			return this.auditionList;
		}

		public void setUrlList(List<UrlList> urlList) {
			this.urlList = urlList;
		}

		public List<UrlList> getUrlList() {
			return this.urlList;
		}

		public void setLlList(List<LlList> llList) {
			this.llList = llList;
		}

		public List<LlList> getLlList() {
			return this.llList;
		}

		public void setMvList(List<MvList> mvList) {
			this.mvList = mvList;
		}

		public List<MvList> getMvList() {
			return this.mvList;
		}

		public void setMvPickCount(int mvPickCount) {
			this.mvPickCount = mvPickCount;
		}

		public int getMvPickCount() {
			return this.mvPickCount;
		}

		public void setMvBulletCount(int mvBulletCount) {
			this.mvBulletCount = mvBulletCount;
		}

		public int getMvBulletCount() {
			return this.mvBulletCount;
		}

		public void setOutFlag(int outFlag) {
			this.outFlag = outFlag;
		}

		public int getOutFlag() {
			return this.outFlag;
		}

		public void setCommentCount(int commentCount) {
			this.commentCount = commentCount;
		}

		public int getCommentCount() {
			return this.commentCount;
		}

		public void setRiskRank(int riskRank) {
			this.riskRank = riskRank;
		}

		public int getRiskRank() {
			return this.riskRank;
		}

		public void setRightKey(RightKey rightKey) {
			this.rightKey = rightKey;
		}

		public RightKey getRightKey() {
			return this.rightKey;
		}

		public void setOperType(int operType) {
			this.operType = operType;
		}

		public int getOperType() {
			return this.operType;
		}

		public void setLevel(String level) {
			this.level = level;
		}

		public String getLevel() {
			return this.level;
		}

		public void setIsExclusive(int isExclusive) {
			this.isExclusive = isExclusive;
		}

		public int getIsExclusive() {
			return this.isExclusive;
		}

		public void setPicUrl(String picUrl) {
			this.picUrl = picUrl;
		}

		public String getPicUrl() {
			return this.picUrl;
		}

		public void setListenCount(int listenCount) {
			this.listenCount = listenCount;
		}

		public int getListenCount() {
			return this.listenCount;
		}

		public void setSingers(List<Singers> singers) {
			this.singers = singers;
		}

		public List<Singers> getSingers() {
			return this.singers;
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

		public class UrlList {
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

		public class LlList {
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

		public class MvList {
			private int id;

			private int songId;

			private int videoId;

			private String picUrl;

			private int durationMilliSecond;

			private int duration;

			private int bitRate;

			private String path;

			private int size;

			private String suffix;

			private int horizontal;

			private int vertical;

			private String url;

			private int type;

			private String typeDescription;

			public void setId(int id) {
				this.id = id;
			}

			public int getId() {
				return this.id;
			}

			public void setSongId(int songId) {
				this.songId = songId;
			}

			public int getSongId() {
				return this.songId;
			}

			public void setVideoId(int videoId) {
				this.videoId = videoId;
			}

			public int getVideoId() {
				return this.videoId;
			}

			public void setPicUrl(String picUrl) {
				this.picUrl = picUrl;
			}

			public String getPicUrl() {
				return this.picUrl;
			}

			public void setDurationMilliSecond(int durationMilliSecond) {
				this.durationMilliSecond = durationMilliSecond;
			}

			public int getDurationMilliSecond() {
				return this.durationMilliSecond;
			}

			public void setDuration(int duration) {
				this.duration = duration;
			}

			public int getDuration() {
				return this.duration;
			}

			public void setBitRate(int bitRate) {
				this.bitRate = bitRate;
			}

			public int getBitRate() {
				return this.bitRate;
			}

			public void setPath(String path) {
				this.path = path;
			}

			public String getPath() {
				return this.path;
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

			public void setHorizontal(int horizontal) {
				this.horizontal = horizontal;
			}

			public int getHorizontal() {
				return this.horizontal;
			}

			public void setVertical(int vertical) {
				this.vertical = vertical;
			}

			public int getVertical() {
				return this.vertical;
			}

			public void setUrl(String url) {
				this.url = url;
			}

			public String getUrl() {
				return this.url;
			}

			public void setType(int type) {
				this.type = type;
			}

			public int getType() {
				return this.type;
			}

			public void setTypeDescription(String typeDescription) {
				this.typeDescription = typeDescription;
			}

			public String getTypeDescription() {
				return this.typeDescription;
			}

		}

		public class RightKey {
			private int price;

			private int paymentUnite;

			private int orderType;

			private List<SongRights> songRights;

			private int loginStatus;

			public void setPrice(int price) {
				this.price = price;
			}

			public int getPrice() {
				return this.price;
			}

			public void setPaymentUnite(int paymentUnite) {
				this.paymentUnite = paymentUnite;
			}

			public int getPaymentUnite() {
				return this.paymentUnite;
			}

			public void setOrderType(int orderType) {
				this.orderType = orderType;
			}

			public int getOrderType() {
				return this.orderType;
			}

			public void setSongRights(List<SongRights> songRights) {
				this.songRights = songRights;
			}

			public List<SongRights> getSongRights() {
				return this.songRights;
			}

			public void setLoginStatus(int loginStatus) {
				this.loginStatus = loginStatus;
			}

			public int getLoginStatus() {
				return this.loginStatus;
			}

		}

		public class SongRights {
			private int bitRate;

			private boolean downFlag;

			private boolean listenFlag;

			private boolean downBuyFlag;

			private boolean listenBuyFlag;

			private int downloadRightFlag;

			private int auditionRightFlag;

			public void setBitRate(int bitRate) {
				this.bitRate = bitRate;
			}

			public int getBitRate() {
				return this.bitRate;
			}

			public void setDownFlag(boolean downFlag) {
				this.downFlag = downFlag;
			}

			public boolean getDownFlag() {
				return this.downFlag;
			}

			public void setListenFlag(boolean listenFlag) {
				this.listenFlag = listenFlag;
			}

			public boolean getListenFlag() {
				return this.listenFlag;
			}

			public void setDownBuyFlag(boolean downBuyFlag) {
				this.downBuyFlag = downBuyFlag;
			}

			public boolean getDownBuyFlag() {
				return this.downBuyFlag;
			}

			public void setListenBuyFlag(boolean listenBuyFlag) {
				this.listenBuyFlag = listenBuyFlag;
			}

			public boolean getListenBuyFlag() {
				return this.listenBuyFlag;
			}

			public void setDownloadRightFlag(int downloadRightFlag) {
				this.downloadRightFlag = downloadRightFlag;
			}

			public int getDownloadRightFlag() {
				return this.downloadRightFlag;
			}

			public void setAuditionRightFlag(int auditionRightFlag) {
				this.auditionRightFlag = auditionRightFlag;
			}

			public int getAuditionRightFlag() {
				return this.auditionRightFlag;
			}

		}

		public class Singers {
			private int singerId;

			private String singerName;

			private int singerSFlag;

			private int shopId;

			public void setSingerId(int singerId) {
				this.singerId = singerId;
			}

			public int getSingerId() {
				return this.singerId;
			}

			public void setSingerName(String singerName) {
				this.singerName = singerName;
			}

			public String getSingerName() {
				return this.singerName;
			}

			public void setSingerSFlag(int singerSFlag) {
				this.singerSFlag = singerSFlag;
			}

			public int getSingerSFlag() {
				return this.singerSFlag;
			}

			public void setShopId(int shopId) {
				this.shopId = shopId;
			}

			public int getShopId() {
				return this.shopId;
			}

		}
	}
}
