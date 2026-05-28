package com.gameshare.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("download_link")
public class DownloadLink {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long gameId;
    private String type;
    private String name;
    private String url;
    private Integer sort;
    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
    @TableLogic
    private Integer deleted;
}
