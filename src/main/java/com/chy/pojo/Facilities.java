package com.chy.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import lombok.Data;

/**
 * @TableName facilities
 */
@TableName(value ="facilities")
@Data
public class Facilities implements Serializable {
    @TableId
    private Integer facilitiesId;

    private Integer parkinglotId;

    private String facilitiesName;

    private Object facilitiesType;

    private Integer facilitiesCoordinates;

    private Object facilitiesStatus;
    @Version
    private Integer version;

    @TableLogic
    private Integer isDeleted;


    private static final long serialVersionUID = 1L;
}