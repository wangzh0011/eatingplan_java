package io.renren.modules.salarytool.controller;

import io.renren.common.utils.R;
import io.renren.modules.salarytool.entity.Reply;
import io.renren.modules.salarytool.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/salaryTool")
public class ReplyController {

    @Autowired
    private ReplyService replyService;

    @RequestMapping("/saveReply")
    public R saveReply(Reply reply) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        reply.setCreateTime(sdf.format(new Date()));
        replyService.save(reply);
        return R.ok();
    }

    @RequestMapping("/getReplyList")
    public R getReplyList(Long cid) {
        List<Reply> list = replyService.queryAllByCid(cid);
        return R.ok().put("replyList",list);
    }

}
