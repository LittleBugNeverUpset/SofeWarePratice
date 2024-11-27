package com.chy.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * @TableName payment
 */

@Data
public class Payment implements Serializable {
    @TableId
    private Integer paymentId;

    private Integer orderId;

    private BigDecimal paymentAmount;

    private Object paymentMethod;

    private Date paymentTime;

    @Version
    private Integer version;

    @TableLogic
    private Integer isDeleted;

    private static final long serialVersionUID = 1L;
}