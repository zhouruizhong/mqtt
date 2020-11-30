package com.zrz.mqtt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zrz.mqtt.entity.CdpfFile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

/**
 *
 * @author 周瑞忠
 */
@Mapper
public interface CdpfFileMapper extends BaseMapper<CdpfFile> {

    /**
     * 通过流水号查询
     * @param caseId 流水号
     * @param isValid 是否有效
     * @return CdpfFile
     */
    @Select("SELECT * FROM CDPF_FILE WHERE CASEID = #{caseId} and IS_VALID = #{isValid} and rownum =1")
    CdpfFile queryCdpfByCaseId(String caseId, String isValid);
}
