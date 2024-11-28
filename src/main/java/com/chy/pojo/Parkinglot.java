package com.chy.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.awt.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * @TableName parkinglot
 */
@TableName(value ="parkinglot")
@Data
public class Parkinglot implements Serializable {
    private Integer parkinglotId;

    private Integer districtId;

    private String parkinglotName;

//    private String parkinglotLocation;

    private Integer parkinglotCapacity;

    private BigDecimal parkinglotPrice;

    private Date parkinglotOpenTime;

    private Date parkinglotCloseTime;

    private Integer isFree;

    private Integer version;

    private Integer isDeleted;

    private static final long serialVersionUID = 1L;
}