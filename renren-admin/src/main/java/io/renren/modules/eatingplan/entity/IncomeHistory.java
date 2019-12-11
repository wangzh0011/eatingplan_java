package io.renren.modules.eatingplan.entity;

import com.baomidou.mybatisplus.annotation.TableName;

@TableName("income_history")
public class IncomeHistory {

    private Long id;

    private Long uid;

    private Long agentUid;//我的代理uid

    private Double income;

    private String type; //0：直接收益 1：二级代理 2:三级代理

    private String status; //+:收入  -:支出

    private String createTime;

    public IncomeHistory(Long uid, Long agentUid, Double income, String type, String createTime, String status) {
        this.uid = uid;
        this.agentUid = agentUid;
        this.income = income;
        this.type = type;
        this.createTime = createTime;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public Long getAgentUid() {
        return agentUid;
    }

    public void setAgentUid(Long agentUid) {
        this.agentUid = agentUid;
    }

    public Double getIncome() {
        return income;
    }

    public void setIncome(Double income) {
        this.income = income;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
