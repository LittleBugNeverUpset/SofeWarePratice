package com.chy.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import lombok.Data;

/**
 * @TableName master
 */
@Data
public class Master implements Serializable {
    @TableId
    private Integer masterId;

    private String masterAccount;

    private String masterName;

    private String masterPassword;

    private Integer masterPermissionLevel;

    @Version
    private Integer version;

    @TableLogic
    private Integer isDeleted;

    private static final long serialVersionUID = 1L;
}