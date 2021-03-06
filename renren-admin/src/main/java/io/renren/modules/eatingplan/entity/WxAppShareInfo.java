package io.renren.modules.eatingplan.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("wxapp_shareview")
public class WxAppShareInfo {

    @TableId
    private Long id;

    private Long uid; //浏览者id

    private Long shareuid; //分享者id

    private String brand; //手机品牌

    private String model; //手机型号

    private String system; //操作系统版本

    private String pixelratio;//设备像素比

    private String language;//微信设置的语言

    private String version; //微信版本号

    private String isPay; //Y  已支付  N 未支付

    private String payId; //支付id

    private String createTime;

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

    public Long getShareuid() {
        return shareuid;
    }

    public void setShareuid(Long shareuid) {
        this.shareuid = shareuid;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getPixelratio() {
        return pixelratio;
    }

    public void setPixelratio(String pixelratio) {
        this.pixelratio = pixelratio;
    }

    public String getIsPay() {
        return isPay;
    }

    public void setIsPay(String isPay) {
        this.isPay = isPay;
    }

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "WxAppShareInfo{" +
                "id=" + id +
                ", uid=" + uid +
                ", shareuid=" + shareuid +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", system='" + system + '\'' +
                ", pixelRatio='" + pixelratio + '\'' +
                ", language='" + language + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}
