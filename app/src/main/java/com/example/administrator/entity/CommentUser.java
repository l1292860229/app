package com.example.administrator.entity;

import java.io.Serializable;

public class CommentUser implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String uid;
	private String nickname;
	private String fid;
	private String fnickname;
	private String content;
	private String headsmall;
	private long createtime;
	public CommentUser(){};
	public CommentUser(String uid,String nickname,String fuid,String fnickname,String content){
		this.uid = uid;
		this.nickname = nickname;
		this.fid = fuid;
		this.fnickname = fnickname;
		this.content = content;
	}
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

	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public String getFnickname() {
		return fnickname;
	}

	public void setFnickname(String fnickname) {
		this.fnickname = fnickname;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getHeadsmall() {
		return headsmall;
	}

	public void setHeadsmall(String headsmall) {
		this.headsmall = headsmall;
	}

	public long getCreatetime() {
		return createtime;
	}

	public void setCreatetime(long createtime) {
		this.createtime = createtime;
	}

	@Override
	public String toString() {
		return "CommentUser{" +
				"id=" + id +
				", uid='" + uid + '\'' +
				", nickname='" + nickname + '\'' +
				", fuid='" + fid + '\'' +
				", fnickname='" + fnickname + '\'' +
				", content='" + content + '\'' +
				", headsmall='" + headsmall + '\'' +
				", createtime=" + createtime +
				'}';
	}
}
