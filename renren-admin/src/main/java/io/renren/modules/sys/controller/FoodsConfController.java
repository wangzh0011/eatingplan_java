package io.renren.modules.sys.controller;

import io.renren.common.annotation.SysLog;
import io.renren.common.exception.RRException;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.sys.entity.FoodsEntity;
import io.renren.modules.sys.service.FoodsConfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/sys/foods")
public class FoodsConfController {

    @Autowired
    private FoodsConfService foodsConfService;

    @Value("${img.location}")
    private String location;

    /**
     * 列表
     * @param params
     * @return
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = foodsConfService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 上传文件
     */
    @RequestMapping("/upload")
    public R upload(@RequestParam("file") MultipartFile file) throws Exception {
//		String url = sysConfigService.getValue("uploadUrl");
        String url = "http://127.0.0.1:8080/upload";
        String size = String.valueOf(file.getSize()/1024) + "K";

        if (file.isEmpty()) {
            throw new RRException("上传文件不能为空");
        }
        String fileName = file.getOriginalFilename();

        // 解决中文问题，liunx下中文路径，图片显示问题
        File dest = new File(location + fileName);
        // 检测是否存在目录
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        System.out.println(dest.getParentFile().canExecute());

        file.transferTo(dest);

//        FoodsEntity foodsEntity = new FoodsEntity();
//        foodsEntity.setImgUrl(location + fileName);//服务器路径
//        foodsEntity.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
//        foodsConfService.saveOrUpdate(foodsEntity);

        url = url + fileName;

        return  R.ok().put("url", location + fileName);
    }

    /**
     * 配置信息
     */
    @RequestMapping("/info/{id}")
    @ResponseBody
    public R info(@PathVariable("id") Long id){
        FoodsEntity foods = foodsConfService.getById(id);

        return R.ok().put("foods", foods);
    }

    /**
     * 保存配置
     */
    @SysLog("保存配置")
    @RequestMapping("/save")
    public R save(@RequestBody FoodsEntity foods){

        foods.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

        ValidatorUtils.validateEntity(foods);

        foodsConfService.saveConfig(foods);

        return R.ok();
    }

    /**
     * 修改配置
     */
    @SysLog("修改配置")
    @RequestMapping("/update")
    public R update(@RequestBody FoodsEntity foods){
        ValidatorUtils.validateEntity(foods);

        foodsConfService.update(foods);

        return R.ok();
    }

    /**
     * 删除配置
     */
    @SysLog("删除配置")
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        foodsConfService.deleteBatch(ids);

        return R.ok();
    }


}
