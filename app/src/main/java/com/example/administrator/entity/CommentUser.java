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
	private String fuid;
	private String fnickname;
	private String content;
	private String headsmall;
	private long createtime;

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

	public String getFuid() {
		return fuid;
	}

	public void setFuid(String fuid) {
		this.fuid = fuid;
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
				", fuid='" + fuid + '\'' +
				", fnickname='" + fnickname + '\'' +
				", content='" + content + '\'' +
				", headsmall='" + headsmall + '\'' +
				", createtime=" + createtime +
				'}';
	}
}
