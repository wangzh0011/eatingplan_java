package io.renren.common.utils;

public class TranslationUtil {

    public static String ageIntervalTranslation(String ageInterval) {
        int age = Integer.valueOf(ageInterval);
        if(age <= 20) {
            return "1,20";
        } else if(age <= 25) {
            return "21,25";
        } else if(age <= 30) {
            return "26,30";
        } else if(age <= 35) {
            return "31,35";
        } else if(age <= 40) {
            return "36,40";
        } else if(age <= 50) {
            return "41,50";
        } else if(age <= 60) {
            return "51,60";
        } else {
            return "";
        }
    }

    public static String ageTranslation(String arg) {
        int age = Integer.valueOf(arg);
        if(age <= 20) {
            return "1";
        } else if(age <= 25) {
            return "2";
        } else if(age <= 30) {
            return "3";
        } else if(age <= 35) {
            return "4";
        } else if(age <= 40) {
            return "5";
        } else if(age <= 50) {
            return "6";
        } else if(age <= 60) {
            return "7";
        } else {
            return "";
        }
    }

}
