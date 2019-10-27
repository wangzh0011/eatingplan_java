package io.renren.modules.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import io.renren.common.utils.R;
import io.renren.modules.eatingplan.entity.Lucky;
import io.renren.modules.eatingplan.entity.LuckyHistory;
import io.renren.modules.eatingplan.entity.Users;
import io.renren.modules.eatingplan.service.LuckyHistoryService;
import io.renren.modules.eatingplan.service.LuckyService;
import io.renren.modules.eatingplan.service.UsersInfoService;
import io.renren.modules.eatingplan.service.WxAppShareInfoService;
import io.renren.modules.sys.entity.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sys/userData")
public class UserDataController {

    @Autowired
    private UsersInfoService usersInfoService;

    @Autowired
    private LuckyHistoryService luckyHistoryService;

    @Autowired
    private LuckyService luckyService;

    @Autowired
    private WxAppShareInfoService wxAppShareInfoService;

    @RequestMapping("/list")
    public List getList(@RequestParam Map<String, Object> params) {

        List<UserData> list = new ArrayList();
        String uid = (String)params.get("id");
        if(uid != null && !"".equals(uid.trim())) {
            list.add(query(Long.valueOf(uid)));
            return list;
        }

        List<Users> usersList = usersInfoService.list();
        for (Users user : usersList) {
            List<Lucky> luckyList = luckyService.query(user.getId());
            if(luckyList.size() == 0) {
                continue;
            }

            UserData userData = setUserData(luckyList,user);

            list.add(userData);
        }
//        PageUtils(list);
        return list;
    }


    public UserData query(@RequestBody Long uid) {
        Users user = usersInfoService.queryByUid(uid).get(0);
        List<Lucky> luckyList = luckyService.query(user.getId());
        return setUserData(luckyList,user);
    }

    @RequestMapping("/resetRedPacket")
    public R resetRedPacket(@RequestBody Long uid) {
        boolean flag = luckyHistoryService.remove(new QueryWrapper<LuckyHistory>().eq("uid",uid));
        if(flag) {
            return R.ok("已完成重置");
        }
        return R.ok("系统错误，请联系管理员。");
    }

    @RequestMapping("/resetMoney")
    public R resetMoney(@RequestBody Long uid) {
        boolean flag = luckyService.update(new UpdateWrapper<Lucky>().set("money",0).eq("uid",uid));
        if(flag) {
            return R.ok("已完成重置");
        }
        return R.ok("系统错误，请联系管理员。");
    }

    /**
     * 红包转换
     * @param goodsType
     * @return
     */
    public int redPacketTransition(String goodsType) {
        switch (goodsType){
            case "0": return 50;
//            case "2": return "100元京东卡";
            case "3": return 2;
            case "4": return 1000;
            case "7": return 20;
        }
        return 0;
    }

    public UserData setUserData(List<Lucky> luckyList,Users user) {
        UserData userData = new UserData();
        int money = luckyList.get(0).getMoney();
        int hasPayNum = wxAppShareInfoService.query(user.getId(),"Y").size();//已支付
        int notPayNum = wxAppShareInfoService.query(user.getId(),"N").size();//未支付
        List<LuckyHistory> luckyHistoryList = luckyHistoryService.query(user.getId());
        int redPacket = 0;
        for (LuckyHistory history : luckyHistoryList) {
            redPacket = redPacket + redPacketTransition(history.getGoodsType());
        }
        userData.setId(user.getId());
        userData.setNickName(user.getNickName());
        userData.setMoney(money);
        userData.setPayNum(hasPayNum);
        userData.setShareNum(hasPayNum + notPayNum);
        userData.setRedPacket(redPacket);

        return userData;
    }

}
