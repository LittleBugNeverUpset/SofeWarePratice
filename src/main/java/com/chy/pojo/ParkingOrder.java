package com.chy.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @TableName parking_order
 */

@Data
public class ParkingOrder implements Serializable {
    @TableId
    private Integer orderId;

    private Integer orderStatus;

    private Date orderCreateTime;

    private Date orderUpdateTime;

    private Integer parkinglotId;

    private Integer slotId;

    private Integer userId;

    private Integer carId;

    @Version
    private Integer version;

    @TableLogic
    private Integer isDeleted;

    private static final long serialVersionUID = 1L;
}