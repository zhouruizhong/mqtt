package com.zrz.mqtt.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zrz.mqtt.entity.Attachment;
import com.zrz.mqtt.entity.CdpfFile;
import com.zrz.mqtt.entity.Clsbqy;
import com.zrz.mqtt.entity.IntelligentApprovalData;
import com.zrz.mqtt.mapper.ClsbqyMapper;
import com.zrz.mqtt.service.AttachmentService;
import com.zrz.mqtt.service.CdpfFileService;
import com.zrz.mqtt.service.ClsbqyService;
import com.zrz.mqtt.service.SignPictureService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 残联申报企业
 * @author zrz
 */
@Service
@DS("master")
public class ClsbqyServiceImpl extends ServiceImpl<ClsbqyMapper, Clsbqy> implements ClsbqyService {

    private static final String PREFIX = "http://192.68.60.231:8881/files/";

    @Resource
    private AttachmentService attachmentService;

    @Resource
    private CdpfFileService cdpfFileService;

    @Resource
    private SignPictureService signPictureService;

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
        for (Map<String, Object> objectMap: list) {
            if (null == objectMap.get("流水号")) {
                continue;
            }
            String _caseId = (String) objectMap.get("流水号");
            // 查询底图
            Attachment attachment = attachmentService.queryAttachmentByCaseId(_caseId);
            if (null != attachment) {
                objectMap.put("bigUrl", PREFIX + attachment.getFileUrl());
            }
            // 查询原合成图
            CdpfFile cdpfFile = cdpfFileService.queryByCaseId(_caseId, "1");
            if (null != cdpfFile) {
                objectMap.put("oldUrl", cdpfFile.getFileUrl());
            }
            //查询签名图
            IntelligentApprovalData data = signPictureService.queryFileByCaseId(_caseId);
            if (null != data) {
                objectMap.put("smallUrl", data.getMaterialFileUrl());
            }
        }
        //获取分页查询后的pageInfo对象数据
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list);
        //pageInfo中获取到的总记录数total：
        pageInfo.getTotal();
        return pageInfo;
    }
}
