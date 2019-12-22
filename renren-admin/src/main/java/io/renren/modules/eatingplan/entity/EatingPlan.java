package io.renren.modules.eatingplan.entity;

import com.baomidou.mybatisplus.annotation.TableName;

@TableName("eating_plan")
public class EatingPlan {

    private Long id;

    private Long uid;

    private String myReport;

    private String createTime;

    private String timeStamp;

    private String status = "0";// 0：未完成指引  1：已完成指引;

    public EatingPlan(Long uid, String myReport, String createTime, String timeStamp) {
        this.uid = uid;
        this.myReport = myReport;
        this.createTime = createTime;
        this.timeStamp = timeStamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMyReport() {
        return myReport;
    }

    public void setMyReport(String myReport) {
        this.myReport = myReport;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
