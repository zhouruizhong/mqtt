package com.zrz.mqtt.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zrz.mqtt.entity.Clsbqy;
import com.zrz.mqtt.mapper.ClsbqyMapper;
import com.zrz.mqtt.service.ClsbqyService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 残联申报企业
 * @author zrz
 */
@Service
@DS("master")
public class ClsbqyServiceImpl extends ServiceImpl<ClsbqyMapper, Clsbqy> implements ClsbqyService {

    @Override
    public PageInfo<Map<String, Object>> queryClsbqyListByPage(Map<String, Object> map){
        int pageNum = Integer.parseInt (map.get("pageNum").toString());
        int pageSize = Integer.parseInt (map.get("pageSize").toString());
        PageHelper.startPage(pageNum, pageSize);
        Clsbqy clsbqy = new Clsbqy();

        Object companyName = map.get("企业名称");
        if (null != map.get("企业名称") && !"".equals(companyName)) {
            clsbqy.set企业名称(companyName.toString());
        }
        Object caseId = map.get("流水号");
        if (null != map.get("流水号") && !"".equals(caseId)) {
            clsbqy.set流水号(caseId.toString());
        }
        Object screenResult = map.get("筛选结果");
        if (null != map.get("筛选结果") && !"".equals(screenResult)) {
            clsbqy.set初步筛选结果(screenResult.toString());
        }
        Object reHandle = map.get("需重新处理");
        if (null != map.get("需重新处理") && !"".equals(reHandle)) {
            clsbqy.set需重新处理(reHandle.toString());
        }
        List<Map<String, Object>> list = this.baseMapper.selectMaps(Wrappers.query(clsbqy));
        //获取分页查询后的pageInfo对象数据
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list);
        //pageInfo中获取到的总记录数total：
        pageInfo.getTotal();
        return pageInfo;
    }
}
