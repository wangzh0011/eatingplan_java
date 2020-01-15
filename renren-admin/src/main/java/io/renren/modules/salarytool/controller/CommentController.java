package io.renren.modules.salarytool.controller;

import io.renren.common.utils.R;
import io.renren.modules.salarytool.entity.Comment;
import io.renren.modules.salarytool.entity.Reply;
import io.renren.modules.salarytool.service.CommentService;
import io.renren.modules.salarytool.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/salaryTool")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private ReplyService replyService;

    @RequestMapping("/saveComment")
    public R saveComment(Comment comment) {
        comment.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        commentService.save(comment);
        return R.ok();
    }

    @RequestMapping("/getCommentList")
    public R getCommentList(String openid) {

        //查询openid数据
        List<Comment> myList = commentService.queryByOpenid(openid);
        //查询所有  排除openid数据
        List<Comment> list = commentService.queryAll(openid);

        List<Comment> commentList = new ArrayList<>();

        if(myList.size() != 0) {
            commentList.addAll(myList);
        }

        commentList.addAll(list);

        //设置每条留言的评论数 和 最新一条评论
        List<Reply> replyList = null;
        for (Comment comment : commentList) {
            replyList = replyService.queryAllByCid(comment.getId());
            comment.setReplyTotalNum(String.valueOf(replyList.size()));
            if(replyList.size() != 0) {
                comment.setReply(replyList.get(0));
            }
        }

        return R.ok().put("commentList",commentList);
    }

    @RequestMapping("/updateComment")
    public R updateComment(Comment comment) {

        List<Comment> list = commentService.queryById(comment.getId());
        comment.setPraise(list.get(0).getPraise() + 1);
        commentService.update(comment);
        return R.ok();
    }


}
