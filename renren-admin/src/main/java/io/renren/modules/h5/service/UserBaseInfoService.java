package io.renren.modules.h5.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.modules.eatingplan.entity.UserBaseInfo;

import java.util.List;


public interface UserBaseInfoService extends IService<UserBaseInfo> {

    List<UserBaseInfo> queryByUserName(String userName);

    List<UserBaseInfo> query(String openid);

    List<UserBaseInfo> queryByUid(Long uid);

    /**
     * 保存配置信息
     */
    boolean save(UserBaseInfo user);

    void update(UserBaseInfo user);

}
