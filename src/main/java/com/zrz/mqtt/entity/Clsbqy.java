package com.zrz.mqtt.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 残联申报企业名单
 * @author zrz
 */

@Data
@Accessors(chain = true)
@TableName("clsbqy")
public class Clsbqy {
    private String 日期;
    private String 序号;
    private String 企业名称;
    private String 流水号;
    private String 标记号;
    private String 是否打印;
    private String 初步筛选结果;
    private String 是否有问题;
    private String 需重新处理;

    @TableField(exist = false)
    private String 打印材料;

}
