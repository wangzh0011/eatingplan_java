package io.renren.modules.salarytool.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.utils.Constant;
import io.renren.common.utils.R;
import io.renren.common.utils.RequestWeixinApi;
import io.renren.common.utils.TranslationUtil;
import io.renren.modules.salarytool.entity.SalaryInfo;
import io.renren.modules.salarytool.service.SalaryInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

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
            if(salaryInfo.getSalary() != null) {
                salaryInfo.setSalaryTemp(salaryInfo.getSalary());
            }
            if(salaryInfo_db.getSalary() != null && !"0".equals(salaryInfo_db.getSalary())) {
                salaryInfo.setSalary(salaryInfo_db.getSalary());
            }
            salaryInfoService.update(salaryInfo);
        }

        List<SalaryInfo> list1 = salaryInfoService.query(salaryInfo.getOpenid());

        return R.ok().put("salaryInfo",list1.get(0));
    }


    @RequestMapping("/getSalaryInfoByCondition")
    public R getSalaryInfo(SalaryInfo salaryInfo) {

        if(salaryInfo.getAge() != null && !"".equals(salaryInfo.getAge())) {
            //计算age区间
            salaryInfo.setAgeInterval(TranslationUtil.ageTranslation(salaryInfo.getAge()));
        }

        List<SalaryInfo> list = salaryInfoService.query(salaryInfo.getOpenid());

        //更新
        if(list.size() > 0) {
            SalaryInfo salaryInfo_db = list.get(0);
            salaryInfo.setId(salaryInfo_db.getId());
            salaryInfo.setUpdateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            if(salaryInfo.getSalary() != null) {
                salaryInfo.setSalaryTemp(salaryInfo.getSalary());
            }
            salaryInfoService.update(salaryInfo);
        }

        List<SalaryInfo> salaryInfoList = salaryInfoService.queryByCondition(salaryInfo);


        int ranking = Integer.valueOf(salaryInfoService.queryRanking(salaryInfo));//排名
        String avgSalary = salaryInfoService.queryAvg(salaryInfo);
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
            for (int i = 1; i <= removenum; i++) {
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
                .put("lessthan",salaryInfoList.size()-ranking < 0 ? 0 : salaryInfoList.size()-ranking)
                .put("avgSalary",avgSalary);
    }

    @RequestMapping("/getMySalaryInfo")
    public R getMySalaryInfo(SalaryInfo salaryInfo) {

        List<SalaryInfo> list = salaryInfoService.query(salaryInfo.getOpenid());
        if(list.size() == 0) return R.error("查无此信息");
        return R.ok().put("salaryInfo",list.get(0));

    }


    @RequestMapping("/test")
    public R test() {

        String[] str = new String[]{"2,2"   ,
                "3,3"   ,
                "3,4"   ,
                "3,5"   ,
                "3,6"   ,
                "3,7"   ,
                "3,8"   ,
                "3,9"   ,
                "3,10"  ,
                "3,11"  ,
                "3,12"  ,
                "3,13"  ,
                "4,14"  ,
                "4,15"  ,
                "4,16"  ,
                "4,17"  ,
                "4,18"  ,
                "4,19"  ,
                "4,20"  ,
                "4,21"  ,
                "4,22"  ,
                "4,23"  ,
                "4,24"  ,
                "5,25"  ,
                "5,26"  ,
                "5,27"  ,
                "5,28"  ,
                "5,29"  ,
                "5,30"  ,
                "5,31"  ,
                "5,32"  ,
                "5,33"  ,
                "5,34"  ,
                "5,35"  ,
                "5,36"  ,
                "6,37"  ,
                "6,38"  ,
                "6,39"  ,
                "6,40"  ,
                "6,41"  ,
                "6,42"  ,
                "6,43"  ,
                "6,44"  ,
                "6,45"  ,
                "6,46"  ,
                "6,47"  ,
                "6,48"  ,
                "6,49"  ,
                "6,50"  ,
                "7,51"  ,
                "7,52"  ,
                "7,53"  ,
                "7,54"  ,
                "7,55"  ,
                "7,56"  ,
                "7,57"  ,
                "7,58"  ,
                "7,59"  ,
                "8,60"  ,
                "8,61"  ,
                "8,62"  ,
                "8,63"  ,
                "8,64"  ,
                "8,65"  ,
                "8,66"  ,
                "8,67"  ,
                "8,68"  ,
                "8,69"  ,
                "8,70"  ,
                "8,71"  ,
                "8,72"  ,
                "9,73"  ,
                "10,74" ,
                "10,75" ,
                "10,76" ,
                "10,77" ,
                "10,78" ,
                "10,79" ,
                "10,80" ,
                "10,81" ,
                "10,82" ,
                "10,83" ,
                "10,84" ,
                "10,85" ,
                "10,86" ,
                "11,87" ,
                "11,88" ,
                "11,89" ,
                "11,90" ,
                "11,91" ,
                "11,92" ,
                "11,93" ,
                "11,94" ,
                "11,95" ,
                "11,96" ,
                "11,97" ,
                "12,98" ,
                "12,99" ,
                "12,100",
                "12,101",
                "12,102",
                "12,103",
                "12,104",
                "12,105",
                "12,106",
                "12,107",
                "12,108",
                "12,109",
                "12,110",
                "12,111",
                "12,112",
                "12,113",
                "13,114",
                "13,115",
                "13,116",
                "13,117",
                "13,118",
                "13,119",
                "13,120",
                "13,121",
                "13,122",
                "14,123",
                "14,124",
                "14,125",
                "14,126",
                "14,127",
                "14,128",
                "14,129",
                "14,130",
                "14,131",
                "14,132",
                "14,133",
                "15,134",
                "15,135",
                "15,136",
                "15,137",
                "15,138",
                "15,139",
                "15,140",
                "15,141",
                "15,142",
                "15,143",
                "15,144",
                "15,145",
                "15,146",
                "15,147",
                "15,148",
                "15,149",
                "15,150",
                "16,151",
                "16,152",
                "16,153",
                "16,154",
                "16,155",
                "16,156",
                "16,157",
                "16,158",
                "16,159",
                "16,160",
                "16,161",
                "16,162",
                "16,163",
                "16,164",
                "16,165",
                "16,166",
                "16,167",
                "16,168",
                "17,169",
                "17,170",
                "17,171",
                "17,172",
                "17,173",
                "17,174",
                "17,175",
                "17,176",
                "17,177",
                "17,178",
                "17,179",
                "17,180",
                "17,181",
                "17,182",
                "17,183",
                "17,184",
                "17,185",
                "18,186",
                "18,187",
                "18,188",
                "18,189",
                "18,190",
                "18,191",
                "18,192",
                "18,193",
                "18,194",
                "18,195",
                "18,196",
                "18,197",
                "18,198",
                "18,199",
                "19,200",
                "19,201",
                "19,202",
                "19,203",
                "19,204",
                "19,205",
                "19,206",
                "19,207",
                "19,208",
                "19,209",
                "19,210",
                "19,211",
                "19,212",
                "19,213",
                "19,214",
                "19,215",
                "19,216",
                "19,217",
                "19,218",
                "19,219",
                "19,220",
                "19,221",
                "20,222",
                "20,223",
                "20,224",
                "20,225",
                "20,226",
                "20,227",
                "20,228",
                "20,229",
                "20,230",
                "20,231",
                "20,232",
                "20,233",
                "20,234",
                "20,235",
                "21,236",
                "21,237",
                "21,238",
                "21,239",
                "21,240",
                "21,241",
                "21,242",
                "21,243",
                "21,244",
                "21,245",
                "21,246",
                "21,247",
                "21,248",
                "21,249",
                "21,250",
                "21,251",
                "21,252",
                "21,253",
                "21,254",
                "21,255",
                "21,256",
                "21,257",
                "22,258",
                "23,259",
                "23,260",
                "23,261",
                "23,262",
                "23,263",
                "23,264",
                "23,265",
                "23,266",
                "23,267",
                "23,268",
                "23,269",
                "23,270",
                "23,271",
                "23,272",
                "23,273",
                "23,274",
                "23,275",
                "23,276",
                "23,277",
                "23,278",
                "23,279",
                "24,280",
                "24,281",
                "24,282",
                "24,283",
                "24,284",
                "24,285",
                "24,286",
                "24,287",
                "24,288",
                "25,289",
                "25,290",
                "25,291",
                "25,292",
                "25,293",
                "25,294",
                "25,295",
                "25,296",
                "25,297",
                "25,298",
                "25,299",
                "25,300",
                "25,301",
                "25,302",
                "25,303",
                "25,304",
                "26,305",
                "26,306",
                "26,307",
                "26,308",
                "26,309",
                "26,310",
                "26,311",
                "27,312",
                "27,313",
                "27,314",
                "27,315",
                "27,316",
                "27,317",
                "27,318",
                "27,319",
                "27,320",
                "27,321",
                "28,322",
                "28,323",
                "28,324",
                "28,325",
                "28,326",
                "28,327",
                "28,328",
                "28,329",
                "28,330",
                "28,331",
                "28,332",
                "28,333",
                "28,334",
                "28,335",
                "29,336",
                "29,337",
                "29,338",
                "29,339",
                "29,340",
                "29,341",
                "29,342",
                "29,343",
                "30,344",
                "30,345",
                "30,346",
                "30,347",
                "30,348",
                "31,349",
                "31,350",
                "31,351",
                "31,352",
                "31,353",
                "31,354",
                "31,355",
                "31,356",
                "31,357",
                "31,358",
                "31,359",
                "31,360",
                "31,361",
                "31,362",
                "31,363",
                "31,364",
                "31,365",
                "31,366",
                "31,367",
                "31,368",
                "31,369",
                "31,370",
                "31,371",
                "32,372",
                "32,373",
                "32,374",
                "32,375",
                "32,376",
                "32,377",
                "32,378",
                "32,379",
                "32,380",
                "32,381",
                "32,382",
                "32,383",
                "32,384",
                "32,385",
                "32,386",
                "32,387",
                "32,388",
                "32,389",
                "32,390",
                "32,391",
                "32,392",
                "32,393",
                "33,394",
                "33,395",
                "33,396",
                "34,397",
                "34,398"
        };

        for (int i = 0; i < str.length; i++) {

            String[] temp = str[i].split(",");
            salaryInfoService.save(new SalaryInfo("2050","16","1",temp[0],temp[1],"1","2050"));
            salaryInfoService.save(new SalaryInfo("2100","16","1",temp[0],temp[1],"1","2100"));
            salaryInfoService.save(new SalaryInfo("2150","16","1",temp[0],temp[1],"2","2150"));
            salaryInfoService.save(new SalaryInfo("2200","16","1",temp[0],temp[1],"2","2200"));
            salaryInfoService.save(new SalaryInfo("2250","16","1",temp[0],temp[1],"3","2250"));
            salaryInfoService.save(new SalaryInfo("2300","16","1",temp[0],temp[1],"3","2300"));
            salaryInfoService.save(new SalaryInfo("2350","16","1",temp[0],temp[1],"3","2350"));
            salaryInfoService.save(new SalaryInfo("2400","16","1",temp[0],temp[1],"4","2400"));
            salaryInfoService.save(new SalaryInfo("2450","16","1",temp[0],temp[1],"5","2450"));
            salaryInfoService.save(new SalaryInfo("2500","16","1",temp[0],temp[1],"1","2500"));
            salaryInfoService.save(new SalaryInfo("2550","16","1",temp[0],temp[1],"1","2550"));
            salaryInfoService.save(new SalaryInfo("2600","16","2",temp[0],temp[1],"1","2600"));
            salaryInfoService.save(new SalaryInfo("2650","16","2",temp[0],temp[1],"2","2650"));
            salaryInfoService.save(new SalaryInfo("2700","16","2",temp[0],temp[1],"2","2700"));
            salaryInfoService.save(new SalaryInfo("2750","16","2",temp[0],temp[1],"3","2750"));
            salaryInfoService.save(new SalaryInfo("2800","16","2",temp[0],temp[1],"3","2800"));
            salaryInfoService.save(new SalaryInfo("2850","16","2",temp[0],temp[1],"3","2850"));
            salaryInfoService.save(new SalaryInfo("2900","16","2",temp[0],temp[1],"4","2900"));
            salaryInfoService.save(new SalaryInfo("2950","16","2",temp[0],temp[1],"5","2950"));
            salaryInfoService.save(new SalaryInfo("3000","16","2",temp[0],temp[1],"1","3000"));
            salaryInfoService.save(new SalaryInfo("3050","16","1",temp[0],temp[1],"1","3050"));
            salaryInfoService.save(new SalaryInfo("3100","16","1",temp[0],temp[1],"1","3100"));
            salaryInfoService.save(new SalaryInfo("3150","16","1",temp[0],temp[1],"2","3150"));
            salaryInfoService.save(new SalaryInfo("3200","16","1",temp[0],temp[1],"2","3200"));
            salaryInfoService.save(new SalaryInfo("3250","16","1",temp[0],temp[1],"3","3250"));
            salaryInfoService.save(new SalaryInfo("3300","16","1",temp[0],temp[1],"3","3300"));
            salaryInfoService.save(new SalaryInfo("3350","16","1",temp[0],temp[1],"3","3350"));
            salaryInfoService.save(new SalaryInfo("3400","16","1",temp[0],temp[1],"4","3400"));
            salaryInfoService.save(new SalaryInfo("3450","16","1",temp[0],temp[1],"5","3450"));
            salaryInfoService.save(new SalaryInfo("3500","25","1",temp[0],temp[1],"1","3500"));
            salaryInfoService.save(new SalaryInfo("3550","25","1",temp[0],temp[1],"1","3550"));
            salaryInfoService.save(new SalaryInfo("3600","25","1",temp[0],temp[1],"1","3600"));
            salaryInfoService.save(new SalaryInfo("3650","25","2",temp[0],temp[1],"2","3650"));
            salaryInfoService.save(new SalaryInfo("3700","25","2",temp[0],temp[1],"2","3700"));
            salaryInfoService.save(new SalaryInfo("3750","25","2",temp[0],temp[1],"3","3750"));
            salaryInfoService.save(new SalaryInfo("3800","25","2",temp[0],temp[1],"3","3800"));
            salaryInfoService.save(new SalaryInfo("3850","25","2",temp[0],temp[1],"3","3850"));
            salaryInfoService.save(new SalaryInfo("3900","25","2",temp[0],temp[1],"4","3900"));
            salaryInfoService.save(new SalaryInfo("3950","25","2",temp[0],temp[1],"5","3950"));
            salaryInfoService.save(new SalaryInfo("4000","25","2",temp[0],temp[1],"1","4000"));
            salaryInfoService.save(new SalaryInfo("4050","25","2",temp[0],temp[1],"1","4050"));
            salaryInfoService.save(new SalaryInfo("4100","25","1",temp[0],temp[1],"1","4100"));
            salaryInfoService.save(new SalaryInfo("4150","25","1",temp[0],temp[1],"2","4150"));
            salaryInfoService.save(new SalaryInfo("4200","25","1",temp[0],temp[1],"2","4200"));
            salaryInfoService.save(new SalaryInfo("4250","25","1",temp[0],temp[1],"3","4250"));
            salaryInfoService.save(new SalaryInfo("4300","25","1",temp[0],temp[1],"3","4300"));
            salaryInfoService.save(new SalaryInfo("4350","25","1",temp[0],temp[1],"3","4350"));
            salaryInfoService.save(new SalaryInfo("4400","25","1",temp[0],temp[1],"4","4400"));
            salaryInfoService.save(new SalaryInfo("4450","25","1",temp[0],temp[1],"5","4450"));
            salaryInfoService.save(new SalaryInfo("4500","25","1",temp[0],temp[1],"1","4500"));
            salaryInfoService.save(new SalaryInfo("4550","25","1",temp[0],temp[1],"1","4550"));
            salaryInfoService.save(new SalaryInfo("4600","25","1",temp[0],temp[1],"1","4600"));
            salaryInfoService.save(new SalaryInfo("4650","25","1",temp[0],temp[1],"2","4650"));
            salaryInfoService.save(new SalaryInfo("4700","25","2",temp[0],temp[1],"2","4700"));
            salaryInfoService.save(new SalaryInfo("4750","25","2",temp[0],temp[1],"3","4750"));
            salaryInfoService.save(new SalaryInfo("4800","25","2",temp[0],temp[1],"3","4800"));
            salaryInfoService.save(new SalaryInfo("4850","25","2",temp[0],temp[1],"3","4850"));
            salaryInfoService.save(new SalaryInfo("4900","25","2",temp[0],temp[1],"4","4900"));
            salaryInfoService.save(new SalaryInfo("4950","25","2",temp[0],temp[1],"5","4950"));
            salaryInfoService.save(new SalaryInfo("5000","25","2",temp[0],temp[1],"1","5000"));
            salaryInfoService.save(new SalaryInfo("5050","25","2",temp[0],temp[1],"1","5050"));
            salaryInfoService.save(new SalaryInfo("5100","25","2",temp[0],temp[1],"1","5100"));
            salaryInfoService.save(new SalaryInfo("5150","25","1",temp[0],temp[1],"2","5150"));
            salaryInfoService.save(new SalaryInfo("5200","25","1",temp[0],temp[1],"2","5200"));
            salaryInfoService.save(new SalaryInfo("5250","25","1",temp[0],temp[1],"3","5250"));
            salaryInfoService.save(new SalaryInfo("5300","25","1",temp[0],temp[1],"3","5300"));
            salaryInfoService.save(new SalaryInfo("5350","25","1",temp[0],temp[1],"3","5350"));
            salaryInfoService.save(new SalaryInfo("5400","25","1",temp[0],temp[1],"4","5400"));
            salaryInfoService.save(new SalaryInfo("5450","25","1",temp[0],temp[1],"5","5450"));
            salaryInfoService.save(new SalaryInfo("5500","25","1",temp[0],temp[1],"1","5500"));
            salaryInfoService.save(new SalaryInfo("5550","26","1",temp[0],temp[1],"1","5550"));
            salaryInfoService.save(new SalaryInfo("5600","26","1",temp[0],temp[1],"1","5600"));
            salaryInfoService.save(new SalaryInfo("5650","26","1",temp[0],temp[1],"2","5650"));
            salaryInfoService.save(new SalaryInfo("5700","26","1",temp[0],temp[1],"2","5700"));
            salaryInfoService.save(new SalaryInfo("5750","26","2",temp[0],temp[1],"3","5750"));
            salaryInfoService.save(new SalaryInfo("5800","26","2",temp[0],temp[1],"3","5800"));
            salaryInfoService.save(new SalaryInfo("5850","26","2",temp[0],temp[1],"3","5850"));
            salaryInfoService.save(new SalaryInfo("5900","26","2",temp[0],temp[1],"4","5900"));
            salaryInfoService.save(new SalaryInfo("5950","26","2",temp[0],temp[1],"5","5950"));
            salaryInfoService.save(new SalaryInfo("6000","26","2",temp[0],temp[1],"1","6000"));
            salaryInfoService.save(new SalaryInfo("6050","26","2",temp[0],temp[1],"1","6050"));
            salaryInfoService.save(new SalaryInfo("6100","26","2",temp[0],temp[1],"1","6100"));
            salaryInfoService.save(new SalaryInfo("6150","26","2",temp[0],temp[1],"2","6150"));
            salaryInfoService.save(new SalaryInfo("6200","26","1",temp[0],temp[1],"2","6200"));
            salaryInfoService.save(new SalaryInfo("6250","26","1",temp[0],temp[1],"3","6250"));
            salaryInfoService.save(new SalaryInfo("6300","26","1",temp[0],temp[1],"3","6300"));
            salaryInfoService.save(new SalaryInfo("6350","26","1",temp[0],temp[1],"3","6350"));
            salaryInfoService.save(new SalaryInfo("6400","26","1",temp[0],temp[1],"4","6400"));
            salaryInfoService.save(new SalaryInfo("6450","30","1",temp[0],temp[1],"5","6450"));
            salaryInfoService.save(new SalaryInfo("6500","30","1",temp[0],temp[1],"1","6500"));
            salaryInfoService.save(new SalaryInfo("6550","30","1",temp[0],temp[1],"1","6550"));
            salaryInfoService.save(new SalaryInfo("6600","30","1",temp[0],temp[1],"1","6600"));
            salaryInfoService.save(new SalaryInfo("6650","30","1",temp[0],temp[1],"2","6650"));
            salaryInfoService.save(new SalaryInfo("6700","30","1",temp[0],temp[1],"2","6700"));
            salaryInfoService.save(new SalaryInfo("6750","30","1",temp[0],temp[1],"3","6750"));
            salaryInfoService.save(new SalaryInfo("6800","30","2",temp[0],temp[1],"3","6800"));
            salaryInfoService.save(new SalaryInfo("6850","30","2",temp[0],temp[1],"3","6850"));
            salaryInfoService.save(new SalaryInfo("6900","30","2",temp[0],temp[1],"4","6900"));
            salaryInfoService.save(new SalaryInfo("6950","30","2",temp[0],temp[1],"5","6950"));
            salaryInfoService.save(new SalaryInfo("7000","30","2",temp[0],temp[1],"1","7000"));
            salaryInfoService.save(new SalaryInfo("7050","30","2",temp[0],temp[1],"1","7050"));
            salaryInfoService.save(new SalaryInfo("7100","30","2",temp[0],temp[1],"1","7100"));
            salaryInfoService.save(new SalaryInfo("7150","30","2",temp[0],temp[1],"2","7150"));
            salaryInfoService.save(new SalaryInfo("7200","30","2",temp[0],temp[1],"2","7200"));
            salaryInfoService.save(new SalaryInfo("7250","30","2",temp[0],temp[1],"3","7250"));
            salaryInfoService.save(new SalaryInfo("7300","30","1",temp[0],temp[1],"3","7300"));
            salaryInfoService.save(new SalaryInfo("7350","30","1",temp[0],temp[1],"3","7350"));
            salaryInfoService.save(new SalaryInfo("7400","30","1",temp[0],temp[1],"4","7400"));
            salaryInfoService.save(new SalaryInfo("7450","30","1",temp[0],temp[1],"5","7450"));
            salaryInfoService.save(new SalaryInfo("7500","30","1",temp[0],temp[1],"1","7500"));
            salaryInfoService.save(new SalaryInfo("7550","30","1",temp[0],temp[1],"1","7550"));
            salaryInfoService.save(new SalaryInfo("7600","30","1",temp[0],temp[1],"1","7600"));
            salaryInfoService.save(new SalaryInfo("7650","30","1",temp[0],temp[1],"2","7650"));
            salaryInfoService.save(new SalaryInfo("7700","30","1",temp[0],temp[1],"2","7700"));
            salaryInfoService.save(new SalaryInfo("7750","30","1",temp[0],temp[1],"3","7750"));
            salaryInfoService.save(new SalaryInfo("7800","30","1",temp[0],temp[1],"3","7800"));
            salaryInfoService.save(new SalaryInfo("7850","30","1",temp[0],temp[1],"3","7850"));
            salaryInfoService.save(new SalaryInfo("7900","30","2",temp[0],temp[1],"4","7900"));
            salaryInfoService.save(new SalaryInfo("7950","30","2",temp[0],temp[1],"5","7950"));
            salaryInfoService.save(new SalaryInfo("8000","30","2",temp[0],temp[1],"1","8000"));
            salaryInfoService.save(new SalaryInfo("8050","30","2",temp[0],temp[1],"1","8050"));
            salaryInfoService.save(new SalaryInfo("8100","30","2",temp[0],temp[1],"1","8100"));
            salaryInfoService.save(new SalaryInfo("8150","30","2",temp[0],temp[1],"2","8150"));
            salaryInfoService.save(new SalaryInfo("8200","30","2",temp[0],temp[1],"2","8200"));
            salaryInfoService.save(new SalaryInfo("8250","30","2",temp[0],temp[1],"3","8250"));
            salaryInfoService.save(new SalaryInfo("8300","30","2",temp[0],temp[1],"3","8300"));
            salaryInfoService.save(new SalaryInfo("8350","30","1",temp[0],temp[1],"3","8350"));
            salaryInfoService.save(new SalaryInfo("8400","30","1",temp[0],temp[1],"4","8400"));
            salaryInfoService.save(new SalaryInfo("8450","30","1",temp[0],temp[1],"5","8450"));
            salaryInfoService.save(new SalaryInfo("8500","30","1",temp[0],temp[1],"1","8500"));
            salaryInfoService.save(new SalaryInfo("8550","30","1",temp[0],temp[1],"1","8550"));
            salaryInfoService.save(new SalaryInfo("8600","30","1",temp[0],temp[1],"1","8600"));
            salaryInfoService.save(new SalaryInfo("8650","30","1",temp[0],temp[1],"2","8650"));
            salaryInfoService.save(new SalaryInfo("8700","30","1",temp[0],temp[1],"2","8700"));
            salaryInfoService.save(new SalaryInfo("8750","30","1",temp[0],temp[1],"3","8750"));
            salaryInfoService.save(new SalaryInfo("8800","30","1",temp[0],temp[1],"3","8800"));
            salaryInfoService.save(new SalaryInfo("8850","30","1",temp[0],temp[1],"3","8850"));
            salaryInfoService.save(new SalaryInfo("8900","30","1",temp[0],temp[1],"4","8900"));
            salaryInfoService.save(new SalaryInfo("8950","30","2",temp[0],temp[1],"5","8950"));
            salaryInfoService.save(new SalaryInfo("9000","30","2",temp[0],temp[1],"1","9000"));
            salaryInfoService.save(new SalaryInfo("9050","30","2",temp[0],temp[1],"1","9050"));
            salaryInfoService.save(new SalaryInfo("9100","30","2",temp[0],temp[1],"1","9100"));
            salaryInfoService.save(new SalaryInfo("9150","30","2",temp[0],temp[1],"2","9150"));
            salaryInfoService.save(new SalaryInfo("9200","30","2",temp[0],temp[1],"2","9200"));
            salaryInfoService.save(new SalaryInfo("9250","30","2",temp[0],temp[1],"3","9250"));
            salaryInfoService.save(new SalaryInfo("9300","30","2",temp[0],temp[1],"3","9300"));
            salaryInfoService.save(new SalaryInfo("9350","30","2",temp[0],temp[1],"3","9350"));
            salaryInfoService.save(new SalaryInfo("9400","30","1",temp[0],temp[1],"4","9400"));
            salaryInfoService.save(new SalaryInfo("9450","30","1",temp[0],temp[1],"5","9450"));
            salaryInfoService.save(new SalaryInfo("9500","30","1",temp[0],temp[1],"1","9500"));
            salaryInfoService.save(new SalaryInfo("9550","30","1",temp[0],temp[1],"1","9550"));
            salaryInfoService.save(new SalaryInfo("9600","30","1",temp[0],temp[1],"1","9600"));
            salaryInfoService.save(new SalaryInfo("9650","30","1",temp[0],temp[1],"2","9650"));
            salaryInfoService.save(new SalaryInfo("9700","30","1",temp[0],temp[1],"2","9700"));
            salaryInfoService.save(new SalaryInfo("9750","30","1",temp[0],temp[1],"3","9750"));
            salaryInfoService.save(new SalaryInfo("9800","30","1",temp[0],temp[1],"3","9800"));
            salaryInfoService.save(new SalaryInfo("9850","30","1",temp[0],temp[1],"3","9850"));
            salaryInfoService.save(new SalaryInfo("9900","30","1",temp[0],temp[1],"4","9900"));
            salaryInfoService.save(new SalaryInfo("9950","30","1",temp[0],temp[1],"5","9950"));
            salaryInfoService.save(new SalaryInfo("10000","30","2",temp[0],temp[1],"1","10000"));
            salaryInfoService.save(new SalaryInfo("10050","30","2",temp[0],temp[1],"1","10050"));
            salaryInfoService.save(new SalaryInfo("10100","30","2",temp[0],temp[1],"1","10100"));
            salaryInfoService.save(new SalaryInfo("10150","30","2",temp[0],temp[1],"2","10150"));
            salaryInfoService.save(new SalaryInfo("10200","30","2",temp[0],temp[1],"2","10200"));
            salaryInfoService.save(new SalaryInfo("10250","30","2",temp[0],temp[1],"3","10250"));
            salaryInfoService.save(new SalaryInfo("10300","35","2",temp[0],temp[1],"3","10300"));
            salaryInfoService.save(new SalaryInfo("10350","35","2",temp[0],temp[1],"3","10350"));
            salaryInfoService.save(new SalaryInfo("10400","35","2",temp[0],temp[1],"4","10400"));
            salaryInfoService.save(new SalaryInfo("10450","35","1",temp[0],temp[1],"5","10450"));
            salaryInfoService.save(new SalaryInfo("10500","35","1",temp[0],temp[1],"1","10500"));
            salaryInfoService.save(new SalaryInfo("10550","35","1",temp[0],temp[1],"1","10550"));
            salaryInfoService.save(new SalaryInfo("10600","35","1",temp[0],temp[1],"1","10600"));
            salaryInfoService.save(new SalaryInfo("10650","35","1",temp[0],temp[1],"2","10650"));
            salaryInfoService.save(new SalaryInfo("10700","35","1",temp[0],temp[1],"2","10700"));
            salaryInfoService.save(new SalaryInfo("10750","35","1",temp[0],temp[1],"3","10750"));
            salaryInfoService.save(new SalaryInfo("10800","35","1",temp[0],temp[1],"3","10800"));



            System.out.println("第" + i + "次");
        }

        System.out.println("已完成");

        return R.ok();
    }


}
