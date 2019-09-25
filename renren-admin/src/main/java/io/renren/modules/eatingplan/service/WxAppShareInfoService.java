package io.renren.modules.eatingplan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.modules.eatingplan.entity.WxAppShareInfo;

import java.util.List;


public interface WxAppShareInfoService extends IService<WxAppShareInfo> {

    List<WxAppShareInfo> query(Long shareuid);

    /**
     * 保存配置信息
     */
    boolean save(WxAppShareInfo shareInfo);

}
