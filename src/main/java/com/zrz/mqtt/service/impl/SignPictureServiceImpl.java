package com.zrz.mqtt.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.zrz.mqtt.entity.IntelligentApprovalData;
import com.zrz.mqtt.mapper.SignPictureMapper;
import com.zrz.mqtt.service.SignPictureService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 周瑞忠
 */
@Service
@DS("oracle")
public class SignPictureServiceImpl implements SignPictureService {

    @Resource
    private SignPictureMapper signPictureMapper;

    @Override
    public IntelligentApprovalData queryFileByCaseId(String caseId) {
        return signPictureMapper.queryDataByTicketId(caseId);
    }
}
