package io.renren.modules.eatingplan.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

@TableName("USER_FOODS_ENTITY")
public class UserFoodsEntity implements Serializable {

    @TableId
    private Long id;

    private Long uid;

    private String type;

    private String foodsArray;

    private String createTime;

    private int myReport = 1;//第几份报告  默认为1

    public int getMyReport() {
        return myReport;
    }

    public void setMyReport(int myReport) {
        this.myReport = myReport;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFoodsArray() {
        return foodsArray;
    }

    public void setFoodsArray(String foodsArray) {
        this.foodsArray = foodsArray;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
