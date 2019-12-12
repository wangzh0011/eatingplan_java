package io.renren.modules.h5.controller;

import io.renren.common.utils.Constant;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.eatingplan.entity.Account;
import io.renren.modules.eatingplan.entity.IncomeHistory;
import io.renren.modules.eatingplan.entity.Users;
import io.renren.modules.eatingplan.service.UsersInfoService;
import io.renren.modules.h5.service.AccountService;
import io.renren.modules.h5.service.IncomeHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/h5")
public class IncomeHistoryController {

    @Autowired
    private IncomeHistoryService incomeHistoryService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private UsersInfoService usersInfoService;

    @RequestMapping("/getInfomations")
    public R getInfomations(Long uid) {

        //账户信息
        List<Account> accountList = accountService.queryByUid(uid);
        Double balance = accountList.get(0).getMoney();//余额
        Double directIncome = accountList.get(0).getDirectIncome();//直接收益
        Double indirectIncome = accountList.get(0).getIndirectIncomeTwo() + accountList.get(0).getIndirectIncomeThree();//间接收益

        //我的团队
        List<Users> userList = usersInfoService.queryByShareUid(uid);
        int myTeamNum = userList.size();

        //今日订单
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        List<IncomeHistory> incomeList = incomeHistoryService.queryByUidAndDate(uid,date);
        Double incomeToday = 0.0;//今日收益
        for (IncomeHistory h : incomeList) {
            incomeToday += h.getIncome();
        }
        int orderNumToday = incomeList.size();//今日新增订单数

        //今日新增代理
        List<Users> agentList = usersInfoService.queryByShareUidAndDate(uid,date);
        int agentNum = agentList.size();

        Map map = new HashMap();
        map.put("balance",balance);
        map.put("directIncome",directIncome);
        map.put("indirectIncome",indirectIncome);
        map.put("myTeamNum",myTeamNum);
        map.put("incomeToday",incomeToday);
        map.put("orderNumToday",orderNumToday);
        map.put("agentNum",agentNum);

        return R.ok().put("infomations",map);

    }

    @RequestMapping("/getIncomeDetails")
    public R getIncomeDetails(@RequestParam Map<String, Object> params){
        PageUtils page = incomeHistoryService.queryPage(params);

        return R.ok().put("page", page);
    }

    @RequestMapping("/getMyTeamDetails")
    public R getMyTeamDetails(@RequestParam Map<String, Object> params){
        PageUtils page = usersInfoService.queryMyTeamPage(params);

        return R.ok().put("page", page);
    }


}
