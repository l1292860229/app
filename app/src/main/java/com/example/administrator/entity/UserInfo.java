package com.example.administrator.entity;

/**
 * Created by Administrator on 2017/1/22.
 */
/**
 * 用户信息
 */
public class UserInfo {
    private String uid;
    //昵称首字拼音字母
    private String sort;
    //手机号,这个字段已经变成ka6id的小写
    private String phone;
    //openfire服务器密码
    private String password;
    //昵称
    private String nickname;
    //头像
    private String headsmall;
    //性别
    private String gender;
    //个性签名
    private String sign;
    //省份
    private String province;
    //城市
    private String city;
    //备注
    private String remark;
    //身份较验唯一识别值
    private String token;
    //商城地址
    private String url;
    //渠道id
    private String quid;
    //用户等级
    private String userdj;
    //平台id
    private String ypid;
    //在身份唯一标识
    private String ka6id;
    //推荐人id
    private String tid;
    //朋友圈背景图
    private String cover;
    //公司主页
    private String companywebsite;
    //行业
    private String industry;
    //公司
    private String company;
    //公司地址
    private String companyaddress;
    //职位
    private String job;
    //可供
    private String provide;
    //需求
    private String demand;
    //电话号码
    private String telephone;


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getQuid() {
        return quid;
    }

    public void setQuid(String quid) {
        this.quid = quid;
    }

    public String getUserdj() {
        return userdj;
    }

    public void setUserdj(String userdj) {
        this.userdj = userdj;
    }

    public String getYpid() {
        return ypid;
    }

    public void setYpid(String ypid) {
        this.ypid = ypid;
    }

    public String getKa6id() {
        return ka6id;
    }

    public void setKa6id(String ka6id) {
        this.ka6id = ka6id;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getCompanywebsite() {
        return companywebsite;
    }

    public void setCompanywebsite(String companywebsite) {
        this.companywebsite = companywebsite;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCompanyaddress() {
        return companyaddress;
    }

    public void setCompanyaddress(String companyaddress) {
        this.companyaddress = companyaddress;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getProvide() {
        return provide;
    }

    public void setProvide(String provide) {
        this.provide = provide;
    }

    public String getDemand() {
        return demand;
    }

    public void setDemand(String demand) {
        this.demand = demand;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "uid='" + uid + '\'' +
                ", sort='" + sort + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                ", headsmall='" + headsmall + '\'' +
                ", gender='" + gender + '\'' +
                ", sign='" + sign + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", remark='" + remark + '\'' +
                ", token='" + token + '\'' +
                ", url='" + url + '\'' +
                ", quid='" + quid + '\'' +
                ", userdj='" + userdj + '\'' +
                ", ypid='" + ypid + '\'' +
                ", ka6id='" + ka6id + '\'' +
                ", tid='" + tid + '\'' +
                ", cover='" + cover + '\'' +
                '}';
    }
}
