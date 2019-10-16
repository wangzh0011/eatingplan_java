package io.renren.modules.eatingplan.controller;

import io.renren.modules.eatingplan.service.LuckyHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/eatingplan")
public class LuckyController {

    @Autowired
    private LuckyHistoryService luckyHistoryService;

    /**
     * 抽奖程序
     * @return
     */
    @RequestMapping("/lucky")
    public String lucky(Long uid) {


        return arithmetic();
    }

    /**
     * 抽奖算法
     * @return
     */
    public static String arithmetic() {
        //定义中奖率分母 百分之
        int probabilityCount = 100000;
        //最小概率值
        String min = "min";
        //最大概率值
        String max = "max";
        Integer tempInt = 0;
        //待中奖商品数组
        Map<String, Map<String,Integer>> prizesMap = new HashMap<>();
        //小程序中对应的奖品序号
        String[] prizeInfoList = new String[]{"0","1","2","3","4","5","6","7"};
        //每个奖对应的中奖概率
        int[] probabilityList = new int[]{5000,20000,999,1,1,67999,5000,1000};
        for (int i = 0; i < prizeInfoList.length; i++) {
            Map<String,Integer> oddsMap = new HashMap<>();
            //最小概率值
            oddsMap.put(min,tempInt);
            tempInt = tempInt + probabilityList[i];
            //最大概率值
            oddsMap.put(max,tempInt);
            prizesMap.put(prizeInfoList[i],oddsMap);
        }


        //随机一个数字
        int index = (int) (Math.random() * probabilityCount);
        Set<String> prizesIds = prizesMap.keySet();
        for(String prizesId : prizesIds){
            Map<String,Integer> oddsMap = prizesMap.get(prizesId);
            Integer minNum = oddsMap.get(min);
            Integer maxNum = oddsMap.get(max);
            //校验index 再哪个商品概率中间
            if(minNum <= index && maxNum > index){
                return prizesId;
            }
        }

        return "5";
    }


}
