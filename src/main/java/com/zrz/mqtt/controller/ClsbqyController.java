package com.zrz.mqtt.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zrz.mqtt.entity.Clsbqy;
import com.zrz.mqtt.mapper.ClsbqyMapper;
import com.zrz.mqtt.utils.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author zrz
 */
@Controller
public class ClsbqyController {

    @Resource
    private ClsbqyMapper mapper;

    @GetMapping("list")
    @ResponseBody
    public Result list(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize,
                       @RequestParam(value = "企业名称", required = false) String 企业名称, @RequestParam(value = "流水号", required = false) String 流水号){
        PageHelper.startPage(pageNum, pageSize);
        Clsbqy clsbqy = new Clsbqy();
        if (StringUtils.isNotBlank(企业名称)) {
            clsbqy.set企业名称(企业名称);
        }
        if (StringUtils.isNotBlank(流水号)) {
            clsbqy.set流水号(流水号);
        }
        List<Map<String, Object>> list = mapper.selectMaps(Wrappers.query(clsbqy));
        //获取分页查询后的pageInfo对象数据
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list);
        //pageInfo中获取到的总记录数total：
        pageInfo.getTotal();
        return Result.success(pageInfo);
    }

}
