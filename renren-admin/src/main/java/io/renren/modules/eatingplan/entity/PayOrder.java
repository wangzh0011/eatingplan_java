package io.renren.modules.eatingplan.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import org.springframework.data.annotation.Id;

/**
 * 已支付订单
 */
@TableName("pay_order")
public class PayOrder {

    @Id
    private Long id;

    private Long uid;

    private String openid;

    private String tradeNo;//订单号

    private String totalFee;//订单金额

    private String type;//支付类型  agent:申请为代理  partner:申请为合伙人  report:报告  tool:热量溢出工具

    private String status;//订单状态 0：未支付 1：已支付

    private String createTime;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(String totalFee) {
        this.totalFee = totalFee;
    }
}
