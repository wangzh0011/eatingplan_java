package io.renren.modules.eatingplan.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import org.springframework.data.annotation.Id;

@TableName("lucky")
public class Lucky {

    @Id
    private Long id;

    private Long uid;

    private int integral;//总积分

    private int times;//已抽奖次数

    private String canAgent;//是否有代理权限

    private String isAgent;//是否已经成为代理

    private int money;//佣金

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getCanAgent() {
        return canAgent;
    }

    public void setCanAgent(String canAgent) {
        this.canAgent = canAgent;
    }

    public String getIsAgent() {
        return isAgent;
    }

    public void setIsAgent(String isAgent) {
        this.isAgent = isAgent;
    }

    public Lucky(Long uid) {
        this.uid = uid;
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

    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }
}
