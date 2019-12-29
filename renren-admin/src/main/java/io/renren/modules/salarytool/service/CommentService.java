package io.renren.modules.salarytool.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.modules.salarytool.entity.Comment;

import java.util.List;


public interface CommentService extends IService<Comment> {

    List<Comment> queryById(Long id);

    List<Comment> queryByUid(Long uid);

    List<Comment> queryAll();

    /**
     * 保存配置信息
     */
    boolean save(Comment comment);

    void update(Comment comment);

}
