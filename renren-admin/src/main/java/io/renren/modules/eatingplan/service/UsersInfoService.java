package io.renren.modules.eatingplan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.modules.eatingplan.entity.Users;

import java.util.List;


public interface UsersInfoService extends IService<Users> {

    List<Users> query(String openid);

    /**
     * 保存配置信息
     */
    boolean save(Users user);

}
