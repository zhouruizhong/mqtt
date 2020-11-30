package com.zrz.mqtt.service;

import com.zrz.mqtt.entity.Attachment;

public interface AttachmentService {

    /**
     * 通过caseId查询
     * @param caseId 流水号
     * @return Attachment
     */
    Attachment queryAttachmentByCaseId(String caseId);
}
