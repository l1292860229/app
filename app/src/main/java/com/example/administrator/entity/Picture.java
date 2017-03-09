package com.example.administrator.entity;

import android.view.View;

import java.io.Serializable;

public class Picture implements Serializable {
	 public  enum  PictureType{
		URL_TYPE,
		LOCAL_TYPE,
		DRAWABLE_TYPE,
		URL_NOTCLICK_TYPE,
		MIPMAP_TYPE
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String originUrl;
	private String smallUrl;
	private PictureType type=PictureType.URL_TYPE;
	//序列化时，忽略这个字段
	private transient View.OnClickListener onClickListener;
	public Picture(){}
	public Picture(String small, String origin) {
		super();
		this.smallUrl = small;
		this.originUrl = origin;
	}
	public Picture(String small, String origin,PictureType type) {
		super();
		this.smallUrl = small;
		this.originUrl = origin;
		this.type = type;
	}
	public Picture(String small, String origin,PictureType type,View.OnClickListener onClickListener) {
		super();
		this.smallUrl = small;
		this.originUrl = origin;
		this.type = type;
		this.onClickListener = onClickListener;
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

	public PictureType getType() {
		return type;
	}

	public void setType(PictureType type) {
		this.type = type;
	}

	public View.OnClickListener getOnClickListener() {
		return onClickListener;
	}

	public void setOnClickListener(View.OnClickListener onClickListener) {
		this.onClickListener = onClickListener;
	}

	@Override
	public String toString() {
		return "Picture{" +
				"originUrl='" + originUrl + '\'' +
				", smallUrl='" + smallUrl + '\'' +
				", type=" + type +
				'}';
	}
}