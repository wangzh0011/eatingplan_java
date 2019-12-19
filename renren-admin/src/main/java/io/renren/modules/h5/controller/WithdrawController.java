package io.renren.modules.h5.controller;

import com.alibaba.fastjson.JSON;
import io.renren.common.utils.R;
import io.renren.common.utils.ResultUtils;
import io.renren.modules.eatingplan.controller.BaseController;
import io.renren.modules.eatingplan.entity.*;
import io.renren.modules.eatingplan.service.UsersInfoService;
import io.renren.modules.h5.service.AccountService;
import io.renren.modules.h5.service.IncomeHistoryService;
import io.renren.modules.h5.service.WithdrawHistoryService;
import io.renren.modules.sys.service.SysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WithdrawController extends BaseController {

    @Value("${img.location}")
    private String location;

    @Autowired
    private WithdrawHistoryService withdrawHistoryService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private UsersInfoService usersInfoService;

    @Autowired
    private SysConfigService sysConfigService;

    /**
     * 上传收款码  并创建提现记录
     * @param image
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/withdraw")
    public R imageUploadOfVessel(
            @RequestParam("image") MultipartFile image, Long uid, Double money) {

        String uploadUrl = sysConfigService.getValue("uploadUrl");

        List<Account> accountList = accountService.queryByUid(uid);
        if(accountList.get(0).getMoney() < money) {
            return R.error("提现金额超出已有余额，请确保操作环境正常，并返回主页重新操作");
        }

        String fileName = null;
        List<Users> usersList = null;
        if(!image.isEmpty()) {
            //上传并返回图片路径
            Result result = (Result) uploadImage(image,location,uid);
            //解析图片路径
            JSON jsonData = (JSON) result.getData();
            Map map = JSON.parseObject(jsonData.toJSONString());
            fileName = (String) map.get("filename");
            usersList = usersInfoService.queryByUid(uid);
            if(usersList.size() != 0) {
                //保存图片路径
                usersList.get(0).setQcCodeUrl(fileName);
                usersInfoService.update(usersList.get(0));
            }
        } else {
            usersList = usersInfoService.queryByUid(uid);
            fileName = usersList.get(0).getQcCodeUrl();
        }

        //查询未结算的订单
        List<WithdrawHistory> list = withdrawHistoryService.queryByUid(uid);
        WithdrawHistory withdrawHistory = new WithdrawHistory();
        if(list.size() == 0) {
            withdrawHistory.setUid(uid);
            withdrawHistory.setMoney(money);
            withdrawHistory.setStatus("0");//未结算
            withdrawHistory.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            withdrawHistoryService.save(withdrawHistory);
        } else {

            return R.error("存在未结算的记录，请耐心等待");
        }

        return R.ok().put("qcCodeUrl", uploadUrl + fileName);
    }

    /**
     * 获取用户上传的收款码
     * @param uid
     * @return
     */
    @RequestMapping("/getWithdrawQcCode")
    public R getWithdrawQcCode(Long uid) {
        String uploadUrl = sysConfigService.getValue("uploadUrl");
        List<Users> list = usersInfoService.queryByUid(uid);
        if(list.size() != 0 ) {
            String qcCode = list.get(0).getQcCodeUrl();
            return R.ok().put("qcCodeUrl", uploadUrl + qcCode);
        }
        return R.error("uid: " + uid + "不存在");
    }

    /**
     * 上传图片
     * @return
     */
    public Object uploadImage(MultipartFile image,String location,Long uid) {
        ResultUtils resultUtils = new ResultUtils();
        if (image.isEmpty()){
            return resultUtils.Error(-1,"上传的文件为空");
        }
        //System.out.println(openid);
        // 获取上传的文件名
        String fileName = image.getOriginalFilename();
        //为文件重命名
        String newfilename = "user" + uid + "/";
        // 获取文件的后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));

        String path = "withdrawQcCode/";
        // 文件上传后的路径
        String filePath = location + path;

        //如果不存在,创建文件夹
        File f = new File(filePath);
        if(!f.exists()){
            f.mkdirs();
        }

        // 解决中文问题，liunx下中文路径，图片显示问题
        // fileName = UUID.randomUUID() + suffixName;
        File dest = new File(filePath + newfilename + suffixName);
        // 检测是否存在目录
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }

        try {
            image.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Map map = new HashMap();
        //数据库存入的路径
        map.put("filename",path + newfilename + suffixName);

        return  resultUtils.Success(JSON.toJSON(map));

    }

}
