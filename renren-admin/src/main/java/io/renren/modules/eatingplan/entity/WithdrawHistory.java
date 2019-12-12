package io.renren.modules.eatingplan.entity;

import com.baomidou.mybatisplus.annotation.TableName;

@TableName("withdraw_history")
public class WithdrawHistory {

    private Long id;

    private Long uid;

    private Double money;//提现金额

    private String status;//订单状态   0：未处理  1：已处理

    private String createTime;

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

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
