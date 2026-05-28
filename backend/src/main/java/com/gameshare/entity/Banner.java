package com.gameshare.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("banner")
public class Banner {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String title;
    private String subtitle;
    private String image;
    private String linkType;
    private String linkValue;
    private Integer sort;
    private Integer status;
    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
    @TableLogic
    private Integer deleted;
}
