package io.renren.modules.eatingplan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.modules.eatingplan.entity.Users;

import java.util.List;


public interface UsersInfoService extends IService<Users> {

    List<Users> queryByUserName(String userName);

    List<Users> query(String openid);

    List<Users> queryByUid(Long uid);

    List<Users> queryByShareUid(Long shareUid);

    List<Users> queryByShareUidAndDate(Long shareUid,String date);

    /**
     * 保存配置信息
     */
    boolean save(Users user);

    boolean update(Users user);

}
