package io.renren.modules.h5.controller;

import com.alibaba.fastjson.JSON;
import io.renren.common.utils.R;
import io.renren.common.utils.ResultUtils;
import io.renren.modules.eatingplan.entity.Account;
import io.renren.modules.eatingplan.entity.IncomeHistory;
import io.renren.modules.eatingplan.entity.Result;
import io.renren.modules.eatingplan.entity.WithdrawHistory;
import io.renren.modules.h5.service.AccountService;
import io.renren.modules.h5.service.IncomeHistoryService;
import io.renren.modules.h5.service.WithdrawHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class FileController {

    @Value("${img.location}")
    private String location;

    @Autowired
    private WithdrawHistoryService withdrawHistoryService;

    @Autowired
    private AccountService accountService;

    /**
     * 上传收款码  并创建提现记录
     * @param image
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/saveQcCode",method = RequestMethod.POST)
    public R imageUploadOfVessel(
            @RequestParam("image") MultipartFile image, Long uid, Double money) {

        List<Account> accountList = accountService.queryByUid(uid);
        if(accountList.get(0).getMoney() < money) {
            return R.error("提现金额超出已有余额，请确保操作环境正常，并返回主页重新界面操作");
        }

        //上传并返回图片路径
        Result result = (Result) uploadImage(image,location);
        //解析图片路径
        JSON jsonData = (JSON) result.getData();
        Map map = JSON.parseObject(jsonData.toJSONString());
        String fileName = (String) map.get("filename");

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
        return R.ok();
    }

    /**
     * 上传图片
     * @return
     */
    public Object uploadImage(MultipartFile image,String location) {
        ResultUtils resultUtils = new ResultUtils();
        if (image.isEmpty()){
            return resultUtils.Error(-1,"上传的文件为空");
        }
        //System.out.println(openid);
        // 获取文件名
        String fileName = image.getOriginalFilename();
        log.info("上传的文件名为：" + fileName);
        String newfilename = getNewFilename();
        // 获取文件的后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        log.info("上传的后缀名为：" + suffixName);

        //日期文件夹
        Date date = new Date();
        String path = new SimpleDateFormat("yyyy/MM/dd/").format(date);

        // 文件上传后的路径
        String filePath = location+path;

        //如果不存在,创建文件夹
        File f = new File(filePath);
        if(!f.exists()){
            f.mkdirs();
        }

        // 解决中文问题，liunx下中文路径，图片显示问题
        // fileName = UUID.randomUUID() + suffixName;
        File dest = new File(filePath + newfilename+suffixName);
        // 检测是否存在目录
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        System.out.println(dest.getParentFile().canExecute());
        log.info("上传成功后的文件路径未：" + filePath + fileName);

        try {
            image.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Map map = new HashMap();
        map.put("filename",path+newfilename+suffixName);

        return  resultUtils.Success(JSON.toJSON(map));

    }

}
