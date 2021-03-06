package io.renren.modules.eatingplan.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.utils.R;
import io.renren.modules.eatingplan.entity.Lucky;
import io.renren.modules.eatingplan.entity.WxAppShareInfo;
import io.renren.modules.eatingplan.service.LuckyService;
import io.renren.modules.eatingplan.service.WxAppShareInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/eatingplan")
public class WxAppShareInfoController extends BaseController{

    @Autowired
    private WxAppShareInfoService wxAppShareInfoService;

    @Autowired
    private LuckyService luckyService;


    /**
     * 保存分享信息
     * @param shareInfo
     */
    @RequestMapping("/setShareInfo")
    public void setShareInfo(WxAppShareInfo shareInfo){
        log.info("WxAppShareInfo ==> " + shareInfo);
        int userNum = wxAppShareInfoService.queryUid(shareInfo.getUid()).size();
        if(userNum == 0) {
            //保存分享信息
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            shareInfo.setCreateTime(sdf.format(new Date()));
            wxAppShareInfoService.save(shareInfo);
            int shareNum = wxAppShareInfoService.query(shareInfo.getShareuid()).size();
            /**为分享者加积分 begin**/
            List<Lucky> list = luckyService.query(shareInfo.getShareuid());
            int luckyNum = list.size();
            //数据库不存在 则新增
            if(luckyNum == 0) {
                Lucky lucky = new Lucky(shareInfo.getShareuid());
                lucky.setIntegral(lucky.getIntegral() + 1);
                lucky.setTotalIntegral(lucky.getTotalIntegral() + 1);
                lucky.setCreateTime(sdf.format(new Date()));
                luckyService.save(lucky);
            } else {
                if(shareNum <= 50) {
                    int integral = list.get(0).getIntegral();
                    int totalIntegral = list.get(0).getTotalIntegral();
                    list.get(0).setIntegral(integral + 1);
                    list.get(0).setTotalIntegral(totalIntegral + 1);
                    luckyService.update(list.get(0));
                }
            }
            /**end**/
        }
    }

    /**
     * 根据shareuid查询已分享人数，拥有积分数，代理权限
     * @param shareuid
     * @return
     */
    @RequestMapping("/getShareInfo")
    public R getShareInfo(Long shareuid){
        Map map = new HashMap();
//        int shareTotalNum = wxAppShareInfoService.query(shareuid).size();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int hasPayNum = wxAppShareInfoService.query(shareuid,"Y").size();//已支付
        int notPayNum = wxAppShareInfoService.query(shareuid,"N").size();//未支付
        //查询积分
        int totalIntegral = 0;//累计获得的积分
        List<Lucky> list = luckyService.query(shareuid);
        int luckyNum = list.size();
        //数据库不存在 则新增
        if(luckyNum == 0) {
            Lucky lucky = new Lucky(shareuid);
            lucky.setCreateTime(sdf.format(new Date()));
            luckyService.save(lucky);
            list = luckyService.query(shareuid);
        } else {
            totalIntegral = list.get(0).getTotalIntegral();
            //如果积分数大于100 则拥有代理权限
            if(totalIntegral > 100) {
                list.get(0).setCanAgent("Y");
                luckyService.update(list.get(0));
            }
        }
//        map.put("shareTotalNum",shareTotalNum);
        map.put("money",list.get(0).getMoney());
        map.put("isAgent",list.get(0).getIsAgent());
        map.put("canAgent",list.get(0).getCanAgent());
        map.put("integral",list.get(0).getIntegral());
        map.put("times",list.get(0).getTimes());
        map.put("hasPayNum",hasPayNum);
        map.put("notPayNum",notPayNum);
        return R.ok().put("shareInfo",map);
    }

    /**
     * 根据uid获取分享数据   按时间分类
     * @param uid
     * @return
     */
    @RequestMapping("/getAgentData")
    public R getAgentData(Long uid){
        Map map = new HashMap();
        //时间格式化
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();

        //昨天
        calendar.add(Calendar.DATE, -1);
        String yesterday = sdf.format(calendar.getTime());

        //前天
        calendar.add(Calendar.DATE, -1);
        String beforeYesterday = sdf.format(calendar.getTime());

        //今天
        String today = sdf.format(new Date());

        int yesterdayHasPay = wxAppShareInfoService.query(uid,"Y",yesterday).size();
        int yesterdayNotPay = wxAppShareInfoService.query(uid,"N",yesterday).size();
        int beforeYesterdayHasPay = wxAppShareInfoService.query(uid,"Y",beforeYesterday).size();
        int beforeYesterdayNotPay = wxAppShareInfoService.query(uid,"N",beforeYesterday).size();
        int todayHasPay = wxAppShareInfoService.query(uid,"Y",today).size();
        int todayNotPay = wxAppShareInfoService.query(uid,"N",today).size();

        map.put("yesterdayHasPay",yesterdayHasPay);
        map.put("yesterdayNotPay",yesterdayNotPay + yesterdayHasPay); //点击量
        map.put("beforeYesterdayHasPay",beforeYesterdayHasPay);
        map.put("beforeYesterdayNotPay",beforeYesterdayNotPay + beforeYesterdayHasPay);//点击量
        map.put("todayHasPay",todayHasPay);
        map.put("todayNotPay",todayNotPay + todayHasPay);//点击量

        return R.ok().put("agentData",map);
    }

    public static void  main(String[] arg){
//        //时间格式化
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        Calendar calendar = Calendar.getInstance();
//
//        //前天
//        calendar.add(Calendar.DATE, -1);
//        String beforeYesterday = sdf.format(calendar.getTime());
//
//        //昨天
//        calendar.add(Calendar.DATE, -1);
//        String yesterday = sdf.format(calendar.getTime());
//
//        System.out.println(beforeYesterday);
//        System.out.println(yesterday);
    }


}
