package com.zrz.mqtt.service;

import com.zrz.mqtt.entity.IntelligentApprovalData;

/**
 * @author 周瑞忠
 */
public interface SignPictureService {

    /**
     * 通过流水号查询
     * @param caseId 流水号
     * @return IntelligentApprovalData
     */
    IntelligentApprovalData queryFileByCaseId(String caseId);
}
