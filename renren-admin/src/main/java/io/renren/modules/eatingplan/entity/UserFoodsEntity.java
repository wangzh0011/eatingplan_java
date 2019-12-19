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

    private String myReport;//第几份报告  默认为1

    private String breakfastArray;

    private String lunchArray;

    private String dinnerArray;

    private String snacksArray;

    private String nightsnackArray;

    public String getBreakfastArray() {
        return breakfastArray;
    }

    public void setBreakfastArray(String breakfastArray) {
        this.breakfastArray = breakfastArray;
    }

    public String getLunchArray() {
        return lunchArray;
    }

    public void setLunchArray(String lunchArray) {
        this.lunchArray = lunchArray;
    }

    public String getDinnerArray() {
        return dinnerArray;
    }

    public void setDinnerArray(String dinnerArray) {
        this.dinnerArray = dinnerArray;
    }

    public String getSnacksArray() {
        return snacksArray;
    }

    public void setSnacksArray(String snacksArray) {
        this.snacksArray = snacksArray;
    }

    public String getNightsnackArray() {
        return nightsnackArray;
    }

    public void setNightsnackArray(String nightsnackArray) {
        this.nightsnackArray = nightsnackArray;
    }

    public String getMyReport() {
        return myReport;
    }

    public void setMyReport(String myReport) {
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
