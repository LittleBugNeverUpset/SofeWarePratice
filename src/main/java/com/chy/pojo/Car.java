package com.chy.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import lombok.Data;

/**
 * @TableName car
 */
@Data
public class Car implements Serializable {
    @TableId
    private Integer carId;

    private Integer userId;

    private Integer parkingStatus;

    private String carPlateNumber;  // 车牌号

    @Version
    private Integer version;

    @TableLogic
    private Integer isDeleted;

    private static final long serialVersionUID = 1L;
}