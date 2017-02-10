package com.example.administrator.entity;

/**
 * Created by Administrator on 2017/1/21.
 */

public class UrlConstants {
    public static final String SERVER_PREFIX = "http://139.224.57.105/im2/index.php";
    //注册地址
    public static final String REGIST = SERVER_PREFIX + "/user/api/registforother";
    //登录地址
    public static final String LOGIN = SERVER_PREFIX + "/user/api/loginforother";
    //修改个人资料地址
    public static final String USER_EDIT = SERVER_PREFIX + "/user/api/edit";
    //获取动态按钮地址
    public static final String USER_GETMENU = SERVER_PREFIX + "/user/api/getmenu";
    //获取商机圈地址
    public static final String FRIEND_SHARELIST = SERVER_PREFIX + "/friend/api/shareList";
    //改变背景图片的地址
    public static final String FRIEND_SETCOVER = SERVER_PREFIX + "/friend/api/setCover";
    //发表评论地址
    public static final String FRIEND_SHAREREPLY = SERVER_PREFIX + "/friend/api/shareReply";
    //赞的地址
    public static final String FRIEND_SHAREPRAISE = SERVER_PREFIX + "/friend/api/sharePraise";
}
