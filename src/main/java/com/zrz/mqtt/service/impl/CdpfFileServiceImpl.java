package com.zrz.mqtt.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zrz.mqtt.entity.CdpfFile;
import com.zrz.mqtt.mapper.CdpfFileMapper;
import com.zrz.mqtt.service.CdpfFileService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author zrz
 */

@Service
@DS("oracle")
public class CdpfFileServiceImpl extends ServiceImpl<CdpfFileMapper, CdpfFile> implements CdpfFileService {

    @Resource
    private CdpfFileMapper cdpfFileMapper;

    @Transactional(rollbackFor = Exception.class)
    public void add(CdpfFile cdpfFile){
        baseMapper.insert(cdpfFile);
    }

    @Override
    public CdpfFile queryByCaseId(String caseId, String isValid) {
        return cdpfFileMapper.queryCdpfByCaseId(caseId, isValid);
    }

}
