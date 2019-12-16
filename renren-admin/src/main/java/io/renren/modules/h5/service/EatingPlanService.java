package io.renren.modules.h5.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.modules.eatingplan.entity.EatingPlan;

import java.util.List;


public interface EatingPlanService extends IService<EatingPlan> {


    List<EatingPlan> queryByUid(Long uid,String myReport);

    List<EatingPlan> queryByUid(Long uid);

    /**
     * 保存配置信息
     */
    boolean save(EatingPlan plan);

    void update(EatingPlan plan);

}
