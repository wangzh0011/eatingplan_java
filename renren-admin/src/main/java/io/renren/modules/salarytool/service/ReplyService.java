package io.renren.modules.salarytool.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.modules.salarytool.entity.Reply;

import java.util.List;


public interface ReplyService extends IService<Reply> {

    List<Reply> queryById(Long id);

    List<Reply> queryByUid(Long uid);

    List<Reply> queryAllByCid(Long cid);

    /**
     * 保存配置信息
     */
    boolean save(Reply reply);

    void update(Reply reply);

}
