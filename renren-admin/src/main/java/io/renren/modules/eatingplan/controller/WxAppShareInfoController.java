package io.renren.modules.eatingplan.controller;

import io.renren.common.utils.R;
import io.renren.modules.eatingplan.entity.WxAppShareInfo;
import io.renren.modules.eatingplan.service.WxAppShareInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/eatingplan")
public class WxAppShareInfoController extends BaseController{

    @Autowired
    private WxAppShareInfoService wxAppShareInfoService;

    /**
     * 保存分享信息
     * @param shareInfo
     */
    @RequestMapping("/setShareInfo")
    public void setShareInfo(WxAppShareInfo shareInfo){
        log.info("WxAppShareInfo ==> " + shareInfo);
        wxAppShareInfoService.save(shareInfo);
    }

    /**
     * 根据shareuid查询已分享人数
     * @param shareuid
     * @return
     */
    @RequestMapping("/getShareInfo")
    public R getShareInfo(Long shareuid){
        Map map = new HashMap();
        int shareTotalNum = wxAppShareInfoService.query(shareuid).size();
        int hasPayNum = wxAppShareInfoService.query(shareuid,"Y").size();//已支付
        int notPayNum = wxAppShareInfoService.query(shareuid,"N").size();//未支付
        map.put("shareTotalNum",shareTotalNum);
        map.put("hasPayNum",hasPayNum);
        map.put("notPayNum",notPayNum);
        return R.ok().put("shareInfo",map);
    }

}
