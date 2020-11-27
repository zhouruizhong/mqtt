package com.zrz.mqtt.service;

import java.util.List;
import java.util.Map;

public interface CdpfFileService {

    List<Map<String, Object>> queryListByPage(Map<String, Object> map);
}
