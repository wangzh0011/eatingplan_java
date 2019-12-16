package io.renren.modules.eatingplan.entity;

import com.baomidou.mybatisplus.annotation.TableName;

@TableName("user_base_info")
public class UserBaseInfo {

    private Long id;

    private Long uid;

    private String gender;//性别 0：未知、1：男、2：女

    private String age;

    private String height;

    private String weight;

    private String baseExpend;//基础代谢

    private String sportExpend;//运动消耗

    private String intakeDaily;//每日摄入

    private String targetWeight;//目标体重

    private String targetDay;//目标天数

    private String myReport;//第几份报告

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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getBaseExpend() {
        return baseExpend;
    }

    public void setBaseExpend(String baseExpend) {
        this.baseExpend = baseExpend;
    }

    public String getSportExpend() {
        return sportExpend;
    }

    public void setSportExpend(String sportExpend) {
        this.sportExpend = sportExpend;
    }

    public String getIntakeDaily() {
        return intakeDaily;
    }

    public void setIntakeDaily(String intakeDaily) {
        this.intakeDaily = intakeDaily;
    }

    public String getTargetWeight() {
        return targetWeight;
    }

    public void setTargetWeight(String targetWeight) {
        this.targetWeight = targetWeight;
    }

    public String getTargetDay() {
        return targetDay;
    }

    public void setTargetDay(String targetDay) {
        this.targetDay = targetDay;
    }
}
