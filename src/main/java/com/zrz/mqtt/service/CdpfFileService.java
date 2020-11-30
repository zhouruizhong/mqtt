package com.zrz.mqtt.service;

import com.zrz.mqtt.entity.CdpfFile;

import java.util.List;
import java.util.Map;

/**
 * @author zrz
 */
public interface CdpfFileService {

    /**
     * 根据流水号查询
     * @param caseId 流水号
     * @param isValid 是否有效
     * @return map
     */
    CdpfFile queryByCaseId(String caseId, String isValid);
}
