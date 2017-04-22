package com.example.administrator.entity;


import java.io.Serializable;
import java.util.ArrayList;

public class FriendsLoopItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 259457862436629649L;
	private int id;
	private String uid;
	private String nickname;
	private String headsmall;
	private String content;
	private ArrayList<Picture> picture;
	private Shareurl shareurl;
	private String lng;
	private String lat;
	private String address;
	private long createtime;
	private int ispraise;
	//点赞列表
	private ArrayList<CommentUser> replylist;
	//评论列表
	private ArrayList<CommentUser> praiselist;
	private int showView;
	private String type;
//
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getHeadsmall() {
		return headsmall;
	}

	public void setHeadsmall(String headsmall) {
		this.headsmall = headsmall;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}


	public long getCreatetime() {
		return createtime;
	}

	public void setCreatetime(long createtime) {
		this.createtime = createtime;
	}

	public int getIspraise() {
		return ispraise;
	}

	public void setIspraise(int ispraise) {
		this.ispraise = ispraise;
	}

	public int getShowView() {
		return showView;
	}

	public void setShowView(int showView) {
		this.showView = showView;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Shareurl getShareurl() {
		return shareurl;
	}

	public void setShareurl(Shareurl shareurl) {
		this.shareurl = shareurl;
	}

	public ArrayList<Picture> getPicture() {
		return picture;
	}

	public void setPicture(ArrayList<Picture> picture) {
		this.picture = picture;
	}

	public ArrayList<CommentUser> getReplylist() {
		return replylist;
	}

	public void setReplylist(ArrayList<CommentUser> replylist) {
		this.replylist = replylist;
	}

	public ArrayList<CommentUser> getPraiselist() {
		return praiselist;
	}

	public void setPraiselist(ArrayList<CommentUser> praiselist) {
		this.praiselist = praiselist;
	}

	@Override
	public String toString() {
		return "FriendsLoopItem{" +
				"id=" + id +
				", uid='" + uid + '\'' +
				", nickname='" + nickname + '\'' +
				", headsmall='" + headsmall + '\'' +
				", content='" + content + '\'' +
				", lng='" + lng + '\'' +
				", lat='" + lat + '\'' +
				", address='" + address + '\'' +
				", createtime=" + createtime +
				", ispraise=" + ispraise +
				", showView=" + showView +
				", type='" + type + '\'' +
				'}';
	}
}
