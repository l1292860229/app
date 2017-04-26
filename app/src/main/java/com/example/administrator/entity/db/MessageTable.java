package com.example.administrator.entity.db;


import com.example.administrator.entity.IMMessage;
import com.example.administrator.enumset.MessageType;
import com.example.administrator.enumset.TypeChat;
import com.example.administrator.util.GsonUtil;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class MessageTable extends RealmObject {
	@PrimaryKey
	private String id; 			//消息ID
	private String uid;
	private String toid;
	private String tag = "";				//消息标识符
	private String bid;
	private String content; //消息的文字内容
	private int typechat =100;//100-单聊 200-群聊 300-临时会话 默认为100
	private int typefile;//1-文字 2-图片 3-声音 4-位置
	private long time;//发送消息的时间,毫秒（服务器生成）
	private String from;
	private String to;
	private String image;
	private String voice;
	private String location;
	private String redpacket;
	private int sendState; // 消息发送成功与否的状态  1 成功, 2 正在发送， 4， 正在下载。0 失败
	private int readState; // 读取消息的状态.
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getVoice() {
		return voice;
	}
	public void setVoice(String voice) {
		this.voice = voice;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getRedpacket() {
		return redpacket;
	}
	public void setRedpacket(String redpacket) {
		this.redpacket = redpacket;
	}
	public int getTypechat() {
		return typechat;
	}
	public void setTypechat(int typechat) {
		this.typechat = typechat;
	}
	public int getTypefile() {
		return typefile;
	}
	public void setTypefile(int typefile) {
		this.typefile = typefile;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public int getSendState() {
		return sendState;
	}
	public void setSendState(int sendState) {
		this.sendState = sendState;
	}
	public int getReadState() {
		return readState;
	}
	public String getBid() {
		return bid;
	}
	public void setBid(String bid) {
		this.bid = bid;
	}
	public void setReadState(int readState) {
		this.readState = readState;
	}
	public String getToid() {
		return toid;
	}
	public void setToid(String toid) {
		this.toid = toid;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public IMMessage getIMMessage(){
		IMMessage imMessage = new IMMessage();
		imMessage.setId(id);
		imMessage.setTag(tag);
		imMessage.setContent(content);
		imMessage.setTypechat(TypeChat.valueOf(typechat));
		imMessage.setTypefile(MessageType.valueOf(typefile));
		imMessage.setTime(time);
		imMessage.setFrom(GsonUtil.parseJsonWithGson(from,IMMessage.UserInfo.class));
		imMessage.setTo(GsonUtil.parseJsonWithGson(to,IMMessage.UserInfo.class));
		imMessage.setImage(GsonUtil.parseJsonWithGson(image,IMMessage.Image.class));
		imMessage.setVoice(GsonUtil.parseJsonWithGson(voice,IMMessage.Voice.class));
		imMessage.setLocation(GsonUtil.parseJsonWithGson(location,IMMessage.Location.class));
		imMessage.setRedpacket(GsonUtil.parseJsonWithGson(redpacket,IMMessage.Redpacket.class));
		return imMessage;
	}

	@Override
	public String toString() {
		return "MessageTable{" +
				"id='" + id + '\'' +
				", uid='" + uid + '\'' +
				", toid='" + toid + '\'' +
				", tag='" + tag + '\'' +
				", bid='" + bid + '\'' +
				", content='" + content + '\'' +
				", typechat=" + typechat +
				", typefile=" + typefile +
				", time=" + time +
				", from='" + from + '\'' +
				", to='" + to + '\'' +
				", image='" + image + '\'' +
				", voice='" + voice + '\'' +
				", location='" + location + '\'' +
				", redpacket='" + redpacket + '\'' +
				", sendState=" + sendState +
				", readState=" + readState +
				'}';
	}
}
