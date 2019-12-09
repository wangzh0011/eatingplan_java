package io.renren.modules.h5.controller;

import io.renren.common.utils.R;
import io.renren.modules.eatingplan.entity.UserBaseInfo;
import io.renren.modules.h5.service.UserBaseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/h5")
public class UserBaseInfoController {

    @Autowired
    private UserBaseInfoService userBaseInfoService;

    @RequestMapping("/saveUserBaseInfo")
    public R saveUserBaseInfo(UserBaseInfo user) {
        if(user == null) {
            return R.error("未获取到用户信息");
        }

        List list = userBaseInfoService.queryByUid(user.getUid());
        if(list.size() > 0) {
            userBaseInfoService.update(user);
        } else {
            userBaseInfoService.save(user);
        }

        return R.ok();
    }

}
