package com.chy.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @TableName captcha
 */
@TableName(value ="captcha")
@Data
public class Captcha implements Serializable {
    private Integer id;

    private String code;

    private Integer generatedByAdminId;

    private Date expirationTime;

    private Integer isUsed;

    private Date createTime;

    private static final long serialVersionUID = 1L;
}