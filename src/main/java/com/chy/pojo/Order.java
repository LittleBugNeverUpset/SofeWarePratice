package com.chy.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @TableName order
 */
@Data
public class Order implements Serializable {
    @TableId
    private Integer orderId;

    private Integer orderStatus;

    private Date orderStartTime;

    private Date orderEndTime;

    private Integer parkinglotId;

    private Integer carId;

    private Integer userId;

    private Double orderValue;

    @Version
    private Integer version;

    @TableLogic
    private Integer isDeleted;

    private static final long serialVersionUID = 1L;
}