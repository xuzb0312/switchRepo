package com.gameshare.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("game_image")
public class GameImage {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long gameId;
    private String url;
    private Integer sort;
    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
    @TableLogic
    private Integer deleted;
}
