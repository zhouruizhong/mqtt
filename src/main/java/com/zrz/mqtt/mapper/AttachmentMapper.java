package com.zrz.mqtt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zrz.mqtt.entity.Attachment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AttachmentMapper extends BaseMapper<Attachment> {

  @Select(
      "SELECT\n"
          + "      T.FILE_ID,\n"
          + "      T.FILE_NAME,\n"
          + "      T.FILE_SIZE,\n"
          + "      T.FILE_TYPE,\n"
          + "      T.FILE_URL,\n"
          + "      T.IS_VALID,\n"
          + "      T.CREATE_DATE, \n"
          + "      T.FILE_SOURCE \n"
          + "  FROM EF_ATTACHMENT T\n"
          + "     LEFT JOIN CGA_CASEMATERIAL T001\n"
          + "         ON T.FILE_ID = T001.ATTACHID\n"
          + "     LEFT JOIN CGA_CASE T002\n"
          + "         ON T001.CASEID = T002.CASEID\n"
          + "     LEFT JOIN EGS_PROJECT_MATERIALS T003\n"
          + "         ON T003.MATERIAL_ID = T001.MODULEID\n"
          + "     LEFT JOIN EGS_PROJECT T004\n"
          + "         ON T004.PROJECT_ID = T002.PROJECTID\n"
          + "  WHERE  T003.PRO_DOC_ID = '007' AND T002.CASEID = #{caseId}\n"
          + "    AND T.IS_VALID = 1 AND ROWNUM = 1\n"
          + "  order by T.CREATE_DATE")
  Attachment queryByCaseId(String caseId);
}
