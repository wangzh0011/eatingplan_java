package io.renren.modules.sys.entity;

/**
 * 用户数据总览
 */
public class UserData {

    private Long id;//用户id

    private String nickName;

    private Integer redPacket;//红包

    private Integer money;//佣金

    private Integer shareNum;//分享人数

    private Integer payNum;//支付人数

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getRedPacket() {
        return redPacket;
    }

    public void setRedPacket(Integer redPacket) {
        this.redPacket = redPacket;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public Integer getShareNum() {
        return shareNum;
    }

    public void setShareNum(Integer shareNum) {
        this.shareNum = shareNum;
    }

    public Integer getPayNum() {
        return payNum;
    }

    public void setPayNum(Integer payNum) {
        this.payNum = payNum;
    }
}
