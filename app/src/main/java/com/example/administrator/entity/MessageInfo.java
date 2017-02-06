package com.example.administrator.entity;


public class MessageInfo{
	private static final long serialVersionUID = -4274108350647182194L;
	private String id; 			//消息ID
	private String tag = "";				//消息标识符
	private String fromid;  // 发送者id
	private String fromname; //发送者name
	private String fromurl; //发送者头像

	private String toid; //接收者，可以是某人，也可以是某个群id
	private String toname;//接收者name
	private String tourl;//接收者头像
	
	private String image;//上传图片
	private String imgUrlS = "";			//小图URL
	private String imgUrlL = "";			//大图URL
	private int imgWidth;				//小图宽度
	private int imgHeight;				//小图高度
	
	private String voice;//声音
	private String voiceUrl = "";		//音频URL
	private int voicetime;//声音时间长度
	private int isReadVoice = 0;
	
	private double mLat = 0;				//纬度
	private double mLng = 0;				//经度
	private String mAddress = "";		//地址
	
	
	private String content; //消息的文字内容

	private String redpacket;//红包
	private String redpacketTitle;//红包标题
	private String redpacketUrl;//红包地址

	private int typechat =100;//100-单聊 200-群聊 300-临时会话 默认为100
	private int typefile;//1-文字 2-图片 3-声音 4-位置
	private long time;//发送消息的时间,毫秒（服务器生成）
	
	private int sendState; // 消息发送成功与否的状态  1 成功, 2 正在发送， 4， 正在下载。0 失败
	private int readState; // 读取消息的状态.
	
	private long sendTime;   // 对方发送的时间
	private long pullTime;	// 得到消息的时间
	private int sampleRate = 8000;		//播放音频采样率

	private String imageString;//收到的图片信息
	private String voiceString ;//收到的语音信息

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

	public String getFromid() {
		return fromid;
	}

	public void setFromid(String fromid) {
		this.fromid = fromid;
	}

	public String getFromname() {
		return fromname;
	}

	public void setFromname(String fromname) {
		this.fromname = fromname;
	}

	public String getFromurl() {
		return fromurl;
	}

	public void setFromurl(String fromurl) {
		this.fromurl = fromurl;
	}

	public String getToid() {
		return toid;
	}

	public void setToid(String toid) {
		this.toid = toid;
	}

	public String getToname() {
		return toname;
	}

	public void setToname(String toname) {
		this.toname = toname;
	}

	public String getTourl() {
		return tourl;
	}

	public void setTourl(String tourl) {
		this.tourl = tourl;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getImgUrlS() {
		return imgUrlS;
	}

	public void setImgUrlS(String imgUrlS) {
		this.imgUrlS = imgUrlS;
	}

	public String getImgUrlL() {
		return imgUrlL;
	}

	public void setImgUrlL(String imgUrlL) {
		this.imgUrlL = imgUrlL;
	}

	public int getImgWidth() {
		return imgWidth;
	}

	public void setImgWidth(int imgWidth) {
		this.imgWidth = imgWidth;
	}

	public int getImgHeight() {
		return imgHeight;
	}

	public void setImgHeight(int imgHeight) {
		this.imgHeight = imgHeight;
	}

	public String getVoice() {
		return voice;
	}

	public void setVoice(String voice) {
		this.voice = voice;
	}

	public String getVoiceUrl() {
		return voiceUrl;
	}

	public void setVoiceUrl(String voiceUrl) {
		this.voiceUrl = voiceUrl;
	}

	public int getVoicetime() {
		return voicetime;
	}

	public void setVoicetime(int voicetime) {
		this.voicetime = voicetime;
	}

	public int getIsReadVoice() {
		return isReadVoice;
	}

	public void setIsReadVoice(int isReadVoice) {
		this.isReadVoice = isReadVoice;
	}

	public double getmLat() {
		return mLat;
	}

	public void setmLat(double mLat) {
		this.mLat = mLat;
	}

	public double getmLng() {
		return mLng;
	}

	public void setmLng(double mLng) {
		this.mLng = mLng;
	}

	public String getmAddress() {
		return mAddress;
	}

	public void setmAddress(String mAddress) {
		this.mAddress = mAddress;
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

	public String getRedpacketTitle() {
		return redpacketTitle;
	}

	public void setRedpacketTitle(String redpacketTitle) {
		this.redpacketTitle = redpacketTitle;
	}

	public String getRedpacketUrl() {
		return redpacketUrl;
	}

	public void setRedpacketUrl(String redpacketUrl) {
		this.redpacketUrl = redpacketUrl;
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

	public void setReadState(int readState) {
		this.readState = readState;
	}

	public long getSendTime() {
		return sendTime;
	}

	public void setSendTime(long sendTime) {
		this.sendTime = sendTime;
	}

	public long getPullTime() {
		return pullTime;
	}

	public void setPullTime(long pullTime) {
		this.pullTime = pullTime;
	}

	public int getSampleRate() {
		return sampleRate;
	}

	public void setSampleRate(int sampleRate) {
		this.sampleRate = sampleRate;
	}

	public String getImageString() {
		return imageString;
	}

	public void setImageString(String imageString) {
		this.imageString = imageString;
	}

	public String getVoiceString() {
		return voiceString;
	}

	public void setVoiceString(String voiceString) {
		this.voiceString = voiceString;
	}
}
