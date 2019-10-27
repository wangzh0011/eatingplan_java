package io.renren.modules.eatingplan.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.additional.query.impl.QueryChainWrapper;
import io.renren.common.utils.GoodsTransition;
import io.renren.common.utils.R;
import io.renren.modules.eatingplan.entity.Lucky;
import io.renren.modules.eatingplan.entity.LuckyHistory;
import io.renren.modules.eatingplan.service.LuckyHistoryService;
import io.renren.modules.eatingplan.service.LuckyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/eatingplan")
public class LuckyController extends BaseController{

    @Autowired
    private LuckyHistoryService luckyHistoryService;

    @Autowired
    private LuckyService luckyService;

    /**
     * 抽奖程序
     * @return
     */
    @RequestMapping("/lucky")
    public R lucky(Long uid) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<Lucky> list = luckyService.query(uid);
        int luckyNum = list.size();
        int times = 0;
        int integral = 0;
        Map map = new HashMap();
        if(luckyNum == 0) {
            Lucky lucky = new Lucky(uid);
            lucky.setCreateTime(sdf.format(new Date()));
            luckyService.save(lucky);
            map.put("luckyType","-1");
            map.put("luckyMessage","积分不足");
            map.put("integral",0);
            return R.ok().put("luckyInfo",map);
        } else {
            //获取已抽奖次数
            times = list.get(0).getTimes();
            //获取总积分
            integral = list.get(0).getIntegral();

            if(integral < 10) {
                map.put("luckyType","-1");
                map.put("luckyMessage","积分不足");
                map.put("integral",integral);
                return R.ok().put("luckyInfo",map);
            }

            //记录抽奖次数
            list.get(0).setTimes(times + 1);

            //开始抽奖
            String luckyType = arithmetic(times + 1);
            //记录中奖信息 非积分类   02347表示奖项集合
            if("02347".contains(luckyType)) {
                LuckyHistory history = new LuckyHistory();
                history.setUid(uid);
                history.setGoods(GoodsTransition.transition(luckyType));
                history.setGoodsType(luckyType);
                history.setCreateTime(sdf.format(new Date()));
                log.info("中奖项：" + GoodsTransition.transition(luckyType));
                luckyHistoryService.save(history);
            }
            //积分类奖品  +15积分  10积分抽一次奖 -10积分
            else if(luckyType.equals("1")) {
                list.get(0).setIntegral(integral + 15 - 10);
            }
            //积分类奖品  +100积分  10积分抽一次奖 -10积分
            else if(luckyType.equals("6")) {
                list.get(0).setIntegral(integral + 100 - 10);
            }
            //未中奖 10积分抽一次奖 -10积分
            else {
                list.get(0).setIntegral(integral - 10);
            }
            //更新抽奖次数和剩余积分
            luckyService.update(list.get(0));

            map.put("luckyType",luckyType);
            map.put("luckyMessage","完成抽奖");
            map.put("integral",list.get(0).getIntegral());
            map.put("times",times + 1);
            return R.ok().put("luckyInfo",map);
        }

    }

    /**
     * 成为代理
     */
    @RequestMapping("/beAgent")
    public boolean beAgent(Long uid) {
        Lucky lucky = luckyService.query(uid).get(0);
        lucky.setIsAgent("Y");
        boolean flag = luckyService.update(lucky);
        return flag;
    }


    /**
     * 获取中奖信息
     */
    @RequestMapping("/getMyGoods")
    public List<LuckyHistory> getMyGoods(Long uid) {
        List<LuckyHistory> list = luckyHistoryService.query(uid);
        return list;
    }

    /**
     * 抽奖算法
     * @return
     */
    public String arithmetic(int times) {
        log.info("第" + times + "次抽奖");
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
                //抽奖10次以下的中奖奖项
                if(times < 10 && (prizesId.equals("5") || prizesId.equals("1"))) {
                    return prizesId;
                }
                //第10次必中50元
                else if(times == 10) {
                    return "0";
                }
                //10次到50次中奖奖项
                else if(times > 10 && times < 50 && !(prizesId.equals("6") || prizesId.equals("2") || prizesId.equals("4"))) {
                    return prizesId;
                }
                //50次到500次中奖奖项
                else if(times >= 50 && times < 500 && !(prizesId.equals("2") || prizesId.equals("4"))) {
                    return prizesId;
                }
                //500次以上
                else if(times >= 500) {
                    return prizesId;
                }
            }
        }

        return "5";
    }


}
