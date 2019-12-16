package io.renren.modules.h5.controller;

import io.renren.common.utils.Constant;
import io.renren.common.utils.R;
import io.renren.modules.eatingplan.controller.BaseController;
import io.renren.modules.eatingplan.entity.UserBaseInfo;
import io.renren.modules.h5.service.UserBaseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/h5")
public class UserBaseInfoController extends BaseController {

    @Autowired
    private UserBaseInfoService userBaseInfoService;

    /**
     * 保存用户基础信息
     * @param userBase
     * @return
     */
    @RequestMapping("/saveUserBaseInfo")
    public R saveUserBaseInfo(UserBaseInfo userBase, HttpServletRequest request) {
        if(userBase == null || userBase.getUid() == null) {
            return R.error("未获取到用户信息");
        }

        //设置session
        String myReport = getSession(request);

        List list = userBaseInfoService.queryByUid(userBase.getUid());
        if(list.size() > 0) {
            return R.error("已设定基础信息，无法修改！");
           // userBaseInfoService.update(userBase);
        } else {
            userBase.setMyReport(myReport);
            userBaseInfoService.save(userBase);
        }

        return R.ok();
    }

    /**
     * 获取信息
     * @param uid
     * @return
     */
    @RequestMapping("/getUserBaseInfo")
    public R getUserBaseInfo(Long uid) {
        List<UserBaseInfo> list = userBaseInfoService.queryByUid(uid);
        if(list.size() > 0) {
            UserBaseInfo userBase = list.get(0);
            return R.ok().put("userBase",userBase);
        }

        return R.error("无uid=" + uid + "对应的信息");
    }

}
