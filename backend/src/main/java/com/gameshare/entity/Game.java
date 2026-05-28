package com.gameshare.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
@TableName("game")
public class Game {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private String platform;
    private String coverImage;
    private String description;
    private String type;
    private Integer status;
    private Integer viewCount;
    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    
    @TableLogic
    private Integer deleted;
    
    // 非数据库字段
    @TableField(exist = false)
    private List<DownloadLink> downloadLinks;
    
    @TableField(exist = false)
    private List<String> images;
    
    @TableField(exist = false)
    private Boolean unlocked = false;  // 用户是否已解锁
}
