package com.chy.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * @TableName completed_order
 */

@Data
public class CompletedOrder implements Serializable {
    @TableId
    private Integer orderId;

    private Integer userId;

    private Integer carId;

    private Integer parkinglotId;

    private Integer slotId;

    private Integer orderStatus;

    private BigDecimal orderValue;

    private Integer paymentMethod;

    private Date paymentTime;

    private Date orderCreateTime;

    private Date orderUpdateTime;

    private Date startTime;

    private Date endTime;

    private Integer durationMinutes;

    private String remarks;

    private BigDecimal totalAmount;

    private Integer isPaid;

    @Version
    private Integer version;

    @TableLogic
    private Integer isDeleted;

    private static final long serialVersionUID = 1L;
}