package com.chy.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import lombok.Data;

/**
 * @TableName parking_slot
 */

@Data
public class ParkingSlot implements Serializable {
    private Integer slotId;

    private Integer parkingslotId;

    private Integer parkinglotId;

    private Integer slotStatus;

    @Version
    private Integer version;

    @TableLogic
    private Integer isDeleted;

    private static final long serialVersionUID = 1L;
}