package com.chy.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import lombok.Data;

/**
 * @TableName distract
 */
@Data
public class Distract implements Serializable {
    @TableId
    private Integer distractId;

    private Integer cityId;

    private String cityName;

    @TableLogic
    private Integer isDeleted;

    private static final long serialVersionUID = 1L;
}