package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.FoodsEntity;

import java.util.Map;

public interface FoodsConfService extends IService<FoodsEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 保存配置信息
     */
    void saveConfig(FoodsEntity config);

    /**
     * 更新配置信息
     */
    void update(FoodsEntity config);

    /**
     * 根据key，更新value
     */
    void updateValueByKey(String key, String value);

    /**
     * 删除配置信息
     */
    void deleteBatch(Long[] ids);
}
