package com.zrz.mqtt.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.zrz.mqtt.entity.Clsbqy;

import java.util.Map;

public interface ClsbqyService {

    /**
     * 查询残联申报企业名单
     * @param map 查询条件
     * @return PageInfo
     */
    public PageInfo<Map<String, Object>> queryClsbqyListByPage(Map<String, Object> map);
}
