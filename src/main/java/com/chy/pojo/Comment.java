package com.chy.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @TableName comment
 */

@Data
public class Comment implements Serializable {
    @TableId
    private Integer commentId;

    private String commentContent;

    private Integer parentCommentId;

    private Integer userId;

    private Integer parkinglotId;

    private Integer rating;

    private Date commentTime;
    @Version
    private Integer version;

    @TableLogic
    private Integer isDeleted;


    private static final long serialVersionUID = 1L;
}