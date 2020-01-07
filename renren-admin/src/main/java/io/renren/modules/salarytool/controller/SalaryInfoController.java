package io.renren.modules.salarytool.controller;

import com.alibaba.fastjson.JSON;
import io.renren.common.utils.Constant;
import io.renren.common.utils.R;
import io.renren.common.utils.RequestWeixinApi;
import io.renren.modules.salarytool.entity.SalaryInfo;
import io.renren.modules.salarytool.service.SalaryInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/salaryTool")
public class SalaryInfoController {

    @Autowired
    private SalaryInfoService salaryInfoService;

    @RequestMapping("/login")
    public R login(String code) throws UnsupportedEncodingException {

        //获取access_token和openid
        String accessTokenUrl = Constant.ACCESS_TOKEN_AUTH.replace("CODE",code);
        String result = (String) RequestWeixinApi.requestApi(accessTokenUrl,"GET",null);
        Map map = (Map)JSON.parse(result);
        String access_token = (String) map.get("access_token");
        String openid = (String) map.get("openid");
        //获取用户基本信息
        String userinfoUrl = Constant.GET_USERINFO.replace("ACCESS_TOKEN",access_token).replace("OPENID",openid);
        String result1 = (String) RequestWeixinApi.requestApi(userinfoUrl,"GET",null);
        Map userInfoMap = (Map) JSON.parse(result1);

        //需修改      ==> modify 2020.1.6
        List<SalaryInfo> list = salaryInfoService.query(openid);
        //更新
        if(list.size() > 0) {
            list.get(0).setLastLoginTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            list.get(0).setOpenid((String)userInfoMap.get("openid"));
            list.get(0).setNickName(new String(String.valueOf(userInfoMap.get("nickname")).getBytes("ISO-8859-1"),"utf-8"));
            list.get(0).setGender(String.valueOf(userInfoMap.get("sex")));
//            list.get(0).setProvince((String)userInfoMap.get("province"));
//            list.get(0).setCity((String)userInfoMap.get("city"));
            list.get(0).setCountry("china");
            list.get(0).setAvatarUrl((String)userInfoMap.get("headimgurl"));
            list.get(0).setUnionid((String)userInfoMap.get("unionid"));
            salaryInfoService.update(list.get(0));
            return R.ok().put("salaryInfo",list.get(0));
        }

        //新建
        SalaryInfo salaryInfo = new SalaryInfo();
        salaryInfo.setOpenid((String)userInfoMap.get("openid"));
        salaryInfo.setNickName(new String(String.valueOf(userInfoMap.get("nickname")).getBytes("ISO-8859-1"),"utf-8"));
        salaryInfo.setGender(String.valueOf(userInfoMap.get("sex")));
//        salaryInfo.setProvince((String)userInfoMap.get("province"));
//        salaryInfo.setCity((String)userInfoMap.get("city"));
        salaryInfo.setCountry("china");
        salaryInfo.setAvatarUrl((String)userInfoMap.get("headimgurl"));
        salaryInfo.setUnionid((String)userInfoMap.get("unionid"));
        salaryInfo.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

        //需修改   ==> modify 2020.1.6
        salaryInfo.setOpenid(openid);
        if(salaryInfoService.save(salaryInfo)) {
            List<SalaryInfo> list1 = salaryInfoService.query(salaryInfo.getOpenid());
            return R.ok().put("salaryInfo",list1.get(0));
        }

        return R.error();

    }

    @RequestMapping("/saveUserInfo")
    public R saveUserInfo(SalaryInfo salaryInfo) {

        List<SalaryInfo> list = salaryInfoService.query(salaryInfo.getOpenid());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        //更新
        if(list.size() > 0) {
            SalaryInfo salaryInfo_db = list.get(0);
//            if(salaryInfo_db.getUpdateTime() != null && salaryInfo_db.getUpdateTime().contains(sdf.format(new Date()))){
//                return R.error("亲，每日只能设置一次哦^_^");
//            }
            salaryInfo.setId(salaryInfo_db.getId());
            salaryInfo.setUpdateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            salaryInfoService.update(salaryInfo);
        }

        List<SalaryInfo> list1 = salaryInfoService.query(salaryInfo.getOpenid());

        return R.ok().put("salaryInfo",list1.get(0));
    }


    @RequestMapping("/getSalaryInfoByCondition")
    public R getSalaryInfo(SalaryInfo salaryInfo) {

        List<SalaryInfo> list = salaryInfoService.query(salaryInfo.getOpenid());

        //更新
        if(list.size() > 0) {
            SalaryInfo salaryInfo_db = list.get(0);
            salaryInfo.setId(salaryInfo_db.getId());
            salaryInfo.setUpdateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            salaryInfo.setSalary(salaryInfo_db.getSalary());
            salaryInfoService.update(salaryInfo);
        }

        List<SalaryInfo> salaryInfoList = salaryInfoService.queryByCondition(salaryInfo);
        int ranking = Integer.valueOf(salaryInfoService.queryRanking(salaryInfo));//排名
        String avgNum = salaryInfoService.queryMoreAvg();
        int num = salaryInfoList.size() / 20;//将数据分成20分  每份需要的数据个数
        List finalSalaryInfoList = new ArrayList<>();

        for (int i = 0; i < salaryInfoList.size(); i++) {
            if(num != 0) {
                if(i % num == 0) {
                    finalSalaryInfoList.add(salaryInfoList.get(i).getSalary());
                }
            } else {
                finalSalaryInfoList.add(salaryInfoList.get(i).getSalary());
            }

        }

        if(finalSalaryInfoList.size() > 20) {
            int removenum = finalSalaryInfoList.size() - 20;//需要移除的个数
            for (int i = 1; i < removenum; i++) {
                if(i == 1) {
                    finalSalaryInfoList.remove(i);
                } else {
                    finalSalaryInfoList.remove(i+2);
                }
            }
        }



        //数字格式化
        double percentTemp = (double) ranking / salaryInfoList.size();
        DecimalFormat df = new DecimalFormat("0%");
        String percent = df.format(percentTemp);

        return R.ok()
                .put("rankingPercent",percent)
                .put("salaryInfoList",finalSalaryInfoList)
                .put("morethan",ranking-1)
                .put("lessthan",salaryInfoList.size()-ranking)
                .put("avgNum",avgNum);
    }

    @RequestMapping("/getMySalaryInfo")
    public R getMySalaryInfo(SalaryInfo salaryInfo) {

        List<SalaryInfo> list = salaryInfoService.query(salaryInfo.getOpenid());
        if(list.size() == 0) return R.error("查无此信息");
        return R.ok().put("salaryInfo",list.get(0));

    }


    public static void main(String[] arg) {

        int a = 50 /20;

        System.out.println(a);

    }


}
