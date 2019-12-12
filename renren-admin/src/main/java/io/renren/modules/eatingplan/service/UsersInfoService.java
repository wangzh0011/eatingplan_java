package io.renren.modules.eatingplan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.eatingplan.entity.Users;

import java.util.List;
import java.util.Map;


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

    PageUtils queryMyTeamPage(Map<String, Object> params);

}
