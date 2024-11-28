package com.chy.dobj;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderInitializationRequest {
    private Integer parkinglotId;  // 停车场ID
    private Integer slotId;        // 停车位ID
    private String carPlateNumber;         // 车辆ID
//    private LocalDateTime startTime;  // 停车开始时间
//    private LocalDateTime endTime;    // 停车结束时间
    // Getter 和 Setter
}
