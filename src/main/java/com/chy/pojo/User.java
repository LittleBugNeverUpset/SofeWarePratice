package com.chy.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @TableName user
 */
@Data
public class User implements Serializable {
    @TableId
    private Integer userId;

    private String userName;

    private String userPassword;

    private Integer userSex;

    private String userEmail;

    private Date userCreateTime;

    private String userIcon;

    @Version
    private Integer version;

    @TableLogic
    private Integer isDeleted;

    private static final long serialVersionUID = 1L;

}