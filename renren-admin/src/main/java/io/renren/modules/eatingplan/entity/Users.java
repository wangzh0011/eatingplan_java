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

    private String type; //用户登录来源  jk 健康计划  fq  赚番茄

    private Long jkId; //来源为健康计划的userID

    private Long fqId; //来源为赚番茄的userID

    private String createTime;

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
