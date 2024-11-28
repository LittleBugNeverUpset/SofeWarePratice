package com.chy.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * @TableName parking_slot
 */
@TableName(value ="parking_slot")
@Data
public class ParkingSlot implements Serializable {
    private Integer slotId;

    private Integer parkingslotId;

    private Integer parkinglotId;

    private Integer slotStatus;

    private Integer version;

    private Integer isDeleted;

    private static final long serialVersionUID = 1L;
}