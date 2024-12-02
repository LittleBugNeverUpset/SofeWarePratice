package com.chy.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @TableName user_logs
 */
@TableName(value ="user_logs")
@Data
public class UserLogs implements Serializable {
    private Integer userLogId;

    private Integer userId;

    private String operationType;

    private String description;

    private Date timestamp;

    private static final long serialVersionUID = 1L;
}