package com.chy.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @TableName captcha
 */

@Data
public class Captcha implements Serializable {
    @TableId
    private Integer id;

    private String code;

    private Integer generatedByAdminId;

    private Integer adminLevel;

    private Date expirationTime;

    private Integer isUsed;

    private Date createTime;

    @Version
    private Integer version;

    @TableLogic
    private Integer isDeleted;

    private static final long serialVersionUID = 1L;
}