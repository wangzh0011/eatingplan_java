/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.common.utils;

/**
 * 常量
 *
 * @author Mark sunlightcs@gmail.com
 */
public class Constant {
	/** 超级管理员ID */
	public static final int SUPER_ADMIN = 1;
    /** 数据权限过滤 */
	public static final String SQL_FILTER = "sql_filter";
    /**
     * 当前页码
     */
    public static final String PAGE = "page";
    /**
     * 每页显示记录数
     */
    public static final String LIMIT = "limit";
    /**
     * 排序字段
     */
    public static final String ORDER_FIELD = "sidx";
    /**
     * 排序方式
     */
    public static final String ORDER = "order";
    /**
     *  升序
     */
    public static final String ASC = "asc";

	/**
	 * 菜单类型
	 */
    public enum MenuType {
        /**
         * 目录
         */
    	CATALOG(0),
        /**
         * 菜单
         */
        MENU(1),
        /**
         * 按钮
         */
        BUTTON(2);

        private int value;

        MenuType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
    
    /**
     * 定时任务状态
     */
    public enum ScheduleStatus {
        /**
         * 正常
         */
    	NORMAL(0),
        /**
         * 暂停
         */
    	PAUSE(1);

        private int value;

        ScheduleStatus(int value) {
            this.value = value;
        }
        
        public int getValue() {
            return value;
        }
    }

    /**
     * 云服务商
     */
    public enum CloudService {
        /**
         * 七牛云
         */
        QINIU(1),
        /**
         * 阿里云
         */
        ALIYUN(2),
        /**
         * 腾讯云
         */
        QCLOUD(3);

        private int value;

        CloudService(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }


    /**小程序相关信息 begin**/
    //appid
    public static String AppId_JK ="wxf818611b94a0c6fe";
    //appsecret
    public static String AppSecret_JK="81c3060c59c643a6a4521cb16f478d0b";
    //appid
    public static String AppId_FQ ="wx18a384bbb3417f19";
    //appsecret
    public static String AppSecret_FQ="1ff8f683f886a58c1dbdb971cfc6b96d";
    //mch_id
    public static String mchId = "1557690121";
    //key
    public static String key = "linweidongjiankanfangqie12345678";
    //通知地址
    public static String notifyUrl = "https://fangqie.top";
    //交易类型
    public static String tradeType = "JSAPI";
    /**end**/

    /**H5相关信息**/
    //appid
    public static String AppId_H5 ="";
    //交易类型 h5
    public static String tradeType_h5 = "MWEB";


    //请求微信接口的方式
    public final static String POST = "POST";
    public final static String GET = "GET";

    public final static String OpenIdUrl_JK = "https://api.weixin.qq.com/sns/jscode2session?appid="+AppId_JK+"&secret="+AppSecret_JK+"&js_code=JSCODE&grant_type=authorization_code";
    public final static String TokenUrl_JK = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+AppId_JK+"&secret="+AppSecret_JK;
    public final static String OpenIdUrl_FQ = "https://api.weixin.qq.com/sns/jscode2session?appid="+AppId_FQ+"&secret="+AppSecret_FQ+"&js_code=JSCODE&grant_type=authorization_code";
    public final static String TokenUrl_FQ = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+AppId_FQ+"&secret="+AppSecret_FQ;
    public final static String templateUrl = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=ACCESS_TOKEN";
    public final static String unifiedorderUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";//统一下单url
    //获取小程序码url
    //https://api.weixin.qq.com/cgi-bin/wxaapp/createwxaqrcode?access_token=ACCESS_TOKEN
    //https://api.weixin.qq.com/wxa/getwxacode?access_token=ACCESS_TOKEN
    //https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=ACCESS_TOKEN
    public final static String WXACODE_URL = "https://api.weixin.qq.com/cgi-bin/wxaapp/createwxaqrcode?access_token=ACCESS_TOKEN";


    //用户类型
    public final static String MEMBER = "2";//会员
    public final static String AGENT = "3";//代理
    public final static String PARTNER = "4";//合伙人
    public final static String CHANNEL = "5";//渠道伙伴

    //收入和支付状态
    public final static String INCOME = "+";
    public final static String COST = "-";

    //收益类型
    public final static String ONE = "0";//直接收益
    public final static String TWO = "1";//二级代理收益
    public final static String THREE = "2";//三级代理收益

}
