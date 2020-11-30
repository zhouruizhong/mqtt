package com.zrz.mqtt.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Attachment {

    @TableId("FILE_ID")
    private String fileId;

    @TableField("FILE_NAME")
    private String fileName;

    @TableField("FILE_SIZE")
    private String fileSize;

    @TableField("FILE_TYPE")
    private String fileType;

    @TableField("FILE_URL")
    private String fileUrl;

    @TableField("IS_VALID")
    private String isValid;

    @TableField("CREATE_DATE")
    private String createDate;

    @TableField("FILE_SOURCE")
    private String fileSource;

}
