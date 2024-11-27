package com.chy.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import lombok.Data;

/**
 * @TableName admin
 */

@Data
public class Admin implements Serializable {
    @TableId
    private Integer adminId;

    private String adminAccount;

    private String adminName;

    private String adminPassword;

    private Integer adminPermissionLevel;

    @Version
    private Integer version;

    @TableLogic
    private Integer isDeleted;


    private static final long serialVersionUID = 1L;
}