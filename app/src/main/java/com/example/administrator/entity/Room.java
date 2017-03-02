package com.example.administrator.entity;

import java.io.Serializable;

public class Room implements Serializable{
	private static final long serialVersionUID = 156464416456564L;
	private String id; //群id
	private String name;//群昵称
	private int count; //群总共的人数
	private String openid;      //群创建者id
	private String creator; //群创建者昵称
	private String mynickname; //用户所在群的昵称
	private int isjoin; //是否加入
	private int role;
	private int getmsg = 1;
	private long createtime;
	private UserInfo[] list;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getMynickname() {
		return mynickname;
	}

	public void setMynickname(String mynickname) {
		this.mynickname = mynickname;
	}

	public int getIsjoin() {
		return isjoin;
	}

	public void setIsjoin(int isjoin) {
		this.isjoin = isjoin;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public int getGetmsg() {
		return getmsg;
	}

	public void setGetmsg(int getmsg) {
		this.getmsg = getmsg;
	}

	public long getCreatetime() {
		return createtime;
	}

	public void setCreatetime(long createtime) {
		this.createtime = createtime;
	}

	public UserInfo[] getList() {
		return list;
	}

	public void setList(UserInfo[] list) {
		this.list = list;
	}

	@Override
	public String toString() {
		return "Room{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", count=" + count +
				", openid='" + openid + '\'' +
				", creator='" + creator + '\'' +
				", mynickname='" + mynickname + '\'' +
				", isjoin=" + isjoin +
				", role=" + role +
				", getmsg=" + getmsg +
				", createtime=" + createtime +
				", list=" + list +
				'}';
	}
}
