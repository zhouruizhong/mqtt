package com.zrz.mqtt.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@TableName("CDPF_FILE")
public class CdpfFile {

    @TableField("ID")
    private String id;

    @TableField("TICKET_ID")
    private String ticketId;

    @TableField("PROJECT_NO")
    private String projectNo;

    @TableField("PRO_DOC_ID")
    private String proDocId;

    @TableField("MATERIAL_FILEURL")
    private String materialFileUrl;

    @TableField("CREATE_TIME")
    private String createTime;

    @TableField("VERSION_ID")
    private Integer versionId;

    @TableField("STATUS")
    private Integer status;

    @TableField("INDEX_NUMBER")
    private Integer indexNumber;

    @TableField("RECORD_ID")
    private String recordId;
}
