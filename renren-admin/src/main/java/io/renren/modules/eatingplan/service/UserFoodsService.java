package io.renren.modules.eatingplan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.modules.eatingplan.entity.UserFoodsEntity;

import java.util.List;

public interface UserFoodsService extends IService<UserFoodsEntity> {

    List queryFoodsInfo(Long uid);

    /**
     * 保存配置信息
     */
    void saveConfig(UserFoodsEntity config);

    /**
     * 更新配置信息
     */
    void update(UserFoodsEntity config);

    /**
     * 根据key，更新value
     */
    void updateValueByKey(String key, String value);

    /**
     * 删除配置信息
     */
    void deleteBatch(Long[] ids);
}
