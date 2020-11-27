package com.zrz.mqtt.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zrz.mqtt.entity.Clsbqy;
import com.zrz.mqtt.mapper.ClsbqyMapper;
import com.zrz.mqtt.service.CdpfFileService;
import com.zrz.mqtt.service.ClsbqyService;
import com.zrz.mqtt.utils.Result;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zrz
 */
@RestController
public class ClsbqyController {

    @Resource
    private ClsbqyService clsbqyService;

    @Resource
    private CdpfFileService cdpfFileService;

    @GetMapping("list")
    public Result list(@RequestParam(required = false) Map<String, Object> map){
        return Result.success(clsbqyService.queryClsbqyListByPage(map));
    }

    @GetMapping("dataList")
    public Result dataList(@RequestParam(required = false) Map<String, Object> map){
        return Result.success(cdpfFileService.queryListByPage(map));
    }
}
