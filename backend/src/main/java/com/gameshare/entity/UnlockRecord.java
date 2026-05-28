package com.gameshare.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("unlock_record")
public class UnlockRecord {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;
    private Long gameId;
    private String adType;
    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
    @TableLogic
    private Integer deleted;
}
