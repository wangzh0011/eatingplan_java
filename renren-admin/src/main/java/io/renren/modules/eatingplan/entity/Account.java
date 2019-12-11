package io.renren.modules.eatingplan.entity;

import com.baomidou.mybatisplus.annotation.TableName;

@TableName("account")
public class Account {

    private Long id;

    private Long uid;

    private Double money;//余额

    private Double totalIncome;//历史总收益

    private Double directIncome;//直接收益

    private Double indirectIncomeTwo;//间接收益  二级代理

    private Double indirectIncomeThree;//间接收益  三级代理

    private String createTime;

    public Account(Long uid, Double money, Double totalIncome, Double directIncome, Double indirectIncomeTwo, Double indirectIncomeThree, String createTime) {
        this.uid = uid;
        this.money = money;
        this.totalIncome = totalIncome;
        this.directIncome = directIncome;
        this.indirectIncomeTwo = indirectIncomeTwo;
        this.indirectIncomeThree = indirectIncomeThree;
        this.createTime = createTime;
    }

    public Double getDirectIncome() {
        return directIncome;
    }

    public void setDirectIncome(Double directIncome) {
        this.directIncome = directIncome;
    }

    public Double getIndirectIncomeTwo() {
        return indirectIncomeTwo;
    }

    public void setIndirectIncomeTwo(Double indirectIncomeTwo) {
        this.indirectIncomeTwo = indirectIncomeTwo;
    }

    public Double getIndirectIncomeThree() {
        return indirectIncomeThree;
    }

    public void setIndirectIncomeThree(Double indirectIncomeThree) {
        this.indirectIncomeThree = indirectIncomeThree;
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

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Double getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(Double totalIncome) {
        this.totalIncome = totalIncome;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
