package com.zrz.mqtt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zrz.mqtt.entity.IntelligentApprovalData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author zrz
 */
@Mapper
public interface SignPictureMapper extends BaseMapper<IntelligentApprovalData> {
    /**
     * 通过流水号查询
     * @param ticketId 流水号，同caseId
     * @return IntelligentApprovalData
     */
    @Select("select * from INTELLIGENT_APPROVAL_DATA where TICKET_ID = #{ticketId} and PRO_DOC_ID = '999' AND ROWNUM = 1")
    IntelligentApprovalData queryDataByTicketId(String ticketId);
}
