package com.chy.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @TableName parkinglot
 */
@Data
public class Parkinglot implements Serializable {
    @TableId
    private Integer parkinglotId;

    private Integer parkinglotDistrictId;

    private String parkinglotDistractName;

    private String parkinglotName;

    private Integer parkinglotLocation;

    private Integer parkinglotNumber;

    private Double parkinglotPrice;

    private Date parkinglotOpenTime;

    private Date parkinglotEndTime;

    private Integer isFree;

    @Version
    private Integer version;

    @TableLogic
    private Integer isDeleted;

    private static final long serialVersionUID = 1L;
}