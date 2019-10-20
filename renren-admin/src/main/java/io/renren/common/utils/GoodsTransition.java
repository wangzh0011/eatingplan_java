package io.renren.common.utils;

/**
 * 中奖类型转换
 */
public class GoodsTransition {

    public static String transition(String luckyType){
        switch (luckyType){
            case "0": return "50元红包";
            case "2": return "100元京东卡";
            case "3": return "2元红包";
            case "4": return "1000元红包";
            case "7": return "20元红包";
        }
        return "";
    }
}
