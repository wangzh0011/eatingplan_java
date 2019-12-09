package io.renren.modules.eatingplan.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("users")
public class Users {

    @TableId
    private Long id;

    private String openid;

    private String userName;

    private String nickName;

    private String type = "0"; // 0：用户 1：流量商

    private Long jkId; //来源为健康计划的userID

    private Long fqId; //来源为赚番茄的userID

    private String createTime;

    private String lastLoginTime;

    private String food;//每日应摄入总量

    private String avatarUrl;

    private String gender;//性别 0：未知、1：男、2：女

    private String province;

    private String city;

    private String country;

    private String status = "0";// 0：未完成指引  1：已完成指引

    private String isPay = "N"; // Y:已支付  N：未支付

    private Long shareUid;//分享者id

    public Long getShareUid() {
        return shareUid;
    }

    public void setShareUid(Long shareUid) {
        this.shareUid = shareUid;
    }

    public String getIsPay() {
        return isPay;
    }

    public void setIsPay(String isPay) {
        this.isPay = isPay;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getJkId() {
        return jkId;
    }

    public void setJkId(Long jkId) {
        this.jkId = jkId;
    }

    public Long getFqId() {
        return fqId;
    }

    public void setFqId(Long fqId) {
        this.fqId = fqId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
