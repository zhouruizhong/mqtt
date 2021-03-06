package com.zrz.mqtt.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@TableName("CDPF_FILE")
public class CdpfFile {

    @TableId("FILE_ID")
    private String fileId;

    @TableField("CASEID")
    private String caseId;

    @TableField("FILE_URL")
    private String fileUrl;

    @TableField("FILE_TYPE")
    private String fileType;

    @TableField("IS_VALID")
    private String isValid;

    @TableField("CREATE_TIME")
    private String createTime;

    @TableField("PRO_DOC_ID")
    private String proDocId;
}
