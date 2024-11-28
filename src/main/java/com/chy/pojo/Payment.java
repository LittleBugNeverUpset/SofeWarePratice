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
 * @TableName payment
 */
@TableName(value ="payment")
@Data
public class Payment implements Serializable {
    private Integer paymentId;

    private Integer orderId;

    private BigDecimal totalAmount;

    private Integer paymentMethod;

    private Date paymentTime;

    private Integer version;

    private Integer isDeleted;

    private static final long serialVersionUID = 1L;
}