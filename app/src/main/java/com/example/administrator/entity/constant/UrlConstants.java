package com.example.administrator.entity.constant;

/**
 * Created by Administrator on 2017/1/21.
 */

public class UrlConstants {
    public static final String SERVER_PREFIX = "http://139.224.57.105/im2/index.php";
    //注册地址
    public static final String USER_REGIST = SERVER_PREFIX + "/user/api/registforother";
    //登录地址
    public static final String USER_LOGIN = SERVER_PREFIX + "/user/api/loginforother";
    //修改个人资料地址
    public static final String USER_EDIT = SERVER_PREFIX + "/user/api/edit";
    //获取动态按钮地址
    public static final String USER_GETMENU = SERVER_PREFIX + "/user/api/getmenu";
    //好友列表的的地址
    public static final String FRIEND_FRIENDSLIST = SERVER_PREFIX + "/user/api/friendList";
    //好友详细信息地址
    public static final String BBS_DETAILFOROTHER = SERVER_PREFIX + "/user/api/detailforother";
    //获取商机圈地址
    public static final String FRIEND_SHARELIST = SERVER_PREFIX + "/friend/api/shareList";
    //改变背景图片的地址
    public static final String FRIEND_SETCOVER = SERVER_PREFIX + "/friend/api/setCover";
    //发表评论地址
    public static final String FRIEND_SHAREREPLY = SERVER_PREFIX + "/friend/api/shareReply";
    //赞的地址
    public static final String FRIEND_SHAREPRAISE = SERVER_PREFIX + "/friend/api/sharePraise";
    //删除朋友的地址
    public static final String FRIEND_SHAREPRAISE_DEL = SERVER_PREFIX + "/friend/api/delete";
    //发送朋友圈的地址
    public static final String FRIEND_SHAREPRAISE_ADD = SERVER_PREFIX + "/friend/api/add";
    //群聊列表的的地址
    public static final String SESSION_SESSIONLIST = SERVER_PREFIX + "/session/api/userSessionList";
    //万人群列表的的地址
    public static final String BBS_BBSLIST = SERVER_PREFIX + "/Bbs/api/bbslist";
    //添加万人群，行业圈地址
    public static final String BBS_BBSADD = SERVER_PREFIX + "/Bbs/api/bbsadd";


    //百万人脉后台
    public static final String BAIWANYUMIN = "shop.wei20.cn";
    //钱包
    public static final String BAIWAN_QIANBAO = "http://"+BAIWANYUMIN+"/gouwu/wish3d/my_tg77.shtml";
    //推广二维码
    public static final String BAIWAN_EWEIMA = "http://"+BAIWANYUMIN+"/upic/2pic.shtml";
    //会员升级
    public static final String BAIWAN_HUIYUANSHENJI = "http://"+BAIWANYUMIN+"/gouwu/wish3d/member_hy.shtml";
    //修改密码
    public static final String BAIWAN_UPDATEPASSWORD = "http://"+BAIWANYUMIN+"/gouwu/wish3d/userpass.shtml";
    //附近的店
    public static final String BAIWAN_FUJINDEDIAN = "http://"+BAIWANYUMIN+"/gouwu/o2o/home.shtml";
    //一元夺宝
    public static final String BAIWAN_YIYUANDUOBAO = "http://"+BAIWANYUMIN+"/yiyuan/index.shtml";
    //游戏
    public static final String BAIWAN_GAME = "http://"+BAIWANYUMIN+"/hd/zhuangpang.shtml";
}
