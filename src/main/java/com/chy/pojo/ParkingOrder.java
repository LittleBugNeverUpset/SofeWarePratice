package com.chy.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @TableName parking_order
 */
@TableName(value ="parking_order")
@Data
public class ParkingOrder implements Serializable {
    private Integer orderId;

    private Integer orderStatus;

    private Date orderCreateTime;

    private Date orderUpdateTime;

    private Integer parkinglotId;

    private Integer slotId;

    private Integer userId;

    private Integer carId;

    private Integer version;

    private Integer isDeleted;

    private static final long serialVersionUID = 1L;
}