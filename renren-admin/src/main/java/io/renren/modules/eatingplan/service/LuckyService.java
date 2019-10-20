package io.renren.modules.eatingplan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.modules.eatingplan.entity.Lucky;

import java.util.List;


public interface LuckyService extends IService<Lucky> {

    List<Lucky> query(Long uid);

    /**
     * 保存配置信息
     */
    boolean save(Lucky info);

    boolean update(Lucky info);

}
