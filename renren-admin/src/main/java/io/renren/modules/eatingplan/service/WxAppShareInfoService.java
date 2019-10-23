package io.renren.modules.eatingplan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.modules.eatingplan.entity.Users;
import io.renren.modules.eatingplan.entity.WxAppShareInfo;

import java.util.List;


public interface WxAppShareInfoService extends IService<WxAppShareInfo> {

    List<WxAppShareInfo> query(Long shareuid);

    /**
     * 根据支付状态查询分享信息
     * @param shareuid
     * @param ispay
     * @return
     */
    List<WxAppShareInfo> query(Long shareuid,String ispay);

    /**
     * 根据支付状态查询分享信息 按日期分类
     * @param shareuid
     * @param ispay
     * @param date
     * @return
     */
    List<WxAppShareInfo> query(Long shareuid,String ispay,String date);

    /**
     * 保存配置信息
     */
    boolean save(WxAppShareInfo shareInfo);

    void update(WxAppShareInfo shareInfo);

}
