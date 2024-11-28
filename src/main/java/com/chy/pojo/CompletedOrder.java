package com.chy.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * @TableName completed_order
 */
@TableName(value ="completed_order")
@Data
public class CompletedOrder implements Serializable {
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

    private Integer version;

    private Integer isDeleted;

    private static final long serialVersionUID = 1L;
}