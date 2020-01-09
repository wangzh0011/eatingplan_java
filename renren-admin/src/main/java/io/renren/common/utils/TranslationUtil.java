package io.renren.common.utils;

public class TranslationUtil {

    public static String ageIntervalTranslation(String ageInterval) {
        switch (ageInterval) {
            case "1": return "1,20";
            case "2": return "21,25";
            case "3": return "26,30";
            case "4": return "31,35";
            case "5": return "36,40";
            case "6": return "41,50";
            case "7": return "51,60";
            default: return "";
        }
    }

}
