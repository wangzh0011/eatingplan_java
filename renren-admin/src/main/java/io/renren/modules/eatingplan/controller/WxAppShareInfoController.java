package io.renren.modules.eatingplan.controller;

import io.renren.common.utils.R;
import io.renren.modules.eatingplan.entity.Lucky;
import io.renren.modules.eatingplan.entity.WxAppShareInfo;
import io.renren.modules.eatingplan.service.LuckyService;
import io.renren.modules.eatingplan.service.WxAppShareInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        //保存分享信息
        wxAppShareInfoService.save(shareInfo);
        /**为分享者加积分 begin**/
        List<Lucky> list = luckyService.query(shareInfo.getShareuid());
        int luckyNum = list.size();
        //数据库不存在 则新增
        if(luckyNum == 0) {
            Lucky lucky = new Lucky(shareInfo.getShareuid());
            lucky.setIntegral(1);
            luckyService.save(lucky);
        } else {
            int integral = list.get(0).getIntegral();
            list.get(0).setIntegral(integral + 1);
            luckyService.update(list.get(0));
        }
        /**end**/
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
        int hasPayNum = wxAppShareInfoService.query(shareuid,"Y").size();//已支付
        int notPayNum = wxAppShareInfoService.query(shareuid,"N").size();//未支付
        //查询积分
        int integral = 0;
        int times = 0;
        List<Lucky> list = luckyService.query(shareuid);
        int luckyNum = list.size();
        //数据库不存在 则新增
        if(luckyNum == 0) {
            Lucky lucky = new Lucky(shareuid);
            luckyService.save(lucky);
        } else {
            integral = list.get(0).getIntegral();
            times = list.get(0).getTimes();
            //如果积分数大于100 则拥有代理权限
            if(integral > 100) {
                list.get(0).setCanAgent("Y");
                luckyService.update(list.get(0));
            }
        }
//        map.put("shareTotalNum",shareTotalNum);
        map.put("money",list.get(0).getMoney());
        map.put("isAgent",list.get(0).getIsAgent());
        map.put("canAgent",list.get(0).getCanAgent());
        map.put("integral",integral);
        map.put("times",times);
        map.put("hasPayNum",hasPayNum);
        map.put("notPayNum",notPayNum);
        return R.ok().put("shareInfo",map);
    }

}
