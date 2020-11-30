package com.zrz.mqtt.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.zrz.mqtt.entity.Attachment;
import com.zrz.mqtt.mapper.AttachmentMapper;
import com.zrz.mqtt.service.AttachmentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@DS("oracle")
public class AttachmentServiceImpl implements AttachmentService {

    @Resource
    private AttachmentMapper attachmentMapper;

    @Override
    public Attachment queryAttachmentByCaseId(String caseId) {
        return attachmentMapper.queryByCaseId(caseId);
    }
}
