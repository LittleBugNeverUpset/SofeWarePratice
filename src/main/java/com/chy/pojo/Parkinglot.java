package com.chy.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.awt.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * @TableName parkinglot
 */

@Data
public class Parkinglot implements Serializable {
    @TableId
    private Integer parkinglotId;

    private Integer districtId;

    private String parkinglotName;

//    private String parkinglotLocation;

    private Integer parkinglotCapacity;

    private BigDecimal parkinglotPrice;

    private Date parkinglotOpenTime;

    private Date parkinglotCloseTime;

    private Integer isFree;

    @Version
    private Integer version;

    @TableLogic
    private Integer isDeleted;

    private static final long serialVersionUID = 1L;
}