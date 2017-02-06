package com.example.administrator.entity;

import java.io.Serializable;

public class Picture implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String originUrl;
	private String smallUrl;
	public Picture(){};
	public Picture(String small, String origin) {
		super();
		this.smallUrl = small;
		this.originUrl = origin;
	}

	public String getOriginUrl() {
		return originUrl;
	}

	public void setOriginUrl(String originUrl) {
		this.originUrl = originUrl;
	}

	public String getSmallUrl() {
		return smallUrl;
	}

	public void setSmallUrl(String smallUrl) {
		this.smallUrl = smallUrl;
	}

	@Override
	public String toString() {
		return "Picture{" +
				"originUrl='" + originUrl + '\'' +
				", smallUrl='" + smallUrl + '\'' +
				'}';
	}
}