package io.renren.modules.salarytool.controller;

import io.renren.common.utils.R;
import io.renren.modules.salarytool.entity.Comment;
import io.renren.modules.salarytool.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/salaryTool")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @RequestMapping("/saveComment")
    public R saveComment(Comment comment) {
        comment.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        commentService.save(comment);
        return R.ok();
    }

    @RequestMapping("/getCommentList")
    public R getCommentList() {
        List<Comment> list = commentService.queryAll();

        return R.ok().put("commentList",list);
    }

    @RequestMapping("/updateComment")
    public R updateComment(Comment comment) {

        List<Comment> list = commentService.queryById(comment.getId());
        comment.setPraise(list.get(0).getPraise() + 1);
        commentService.update(comment);
        return R.ok();
    }


}
