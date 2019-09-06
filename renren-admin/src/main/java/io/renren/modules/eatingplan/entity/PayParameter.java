package io.renren.modules.eatingplan.entity;

public class PayParameter {

    private String timeStamp;

    private String nonceStr;

    private String package_pay;

    private String singTpye;

    private String paySign;

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getSingTpye() {
        return singTpye;
    }

    public void setSingTpye(String singTpye) {
        this.singTpye = singTpye;
    }

    public String getPaySign() {
        return paySign;
    }

    public void setPaySign(String paySign) {
        this.paySign = paySign;
    }

    public String getPackage_pay() {
        return package_pay;
    }

    public void setPackage_pay(String package_pay) {
        this.package_pay = package_pay;
    }
}
