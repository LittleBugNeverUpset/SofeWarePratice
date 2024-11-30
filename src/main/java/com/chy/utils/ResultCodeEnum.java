package com.chy.utils;

/**
 * 统一返回结果状态信息类
 *
 */
public enum ResultCodeEnum {

    SUCCESS(200,"success"),
//    NOTLOGIN(501,"notLogin"),
    UNAUTHROIZED(501,"Unauthorized"),
    UPDATE_FIELD_FAILED(502,"Feild to update Information"),
    USERNAME_ERROR(503,"username Error"),
    PASSWORD_ERROR(504,"password Error"),
    USERNAME_USED(505,"user Name Used"),
    USEREMAIL_USED(506,"user Email Used"),
    INVALID_PARAMS(507,"invalid Params"),
    FORBIDDEN(508,"forbidden to update Other User"),
    USER_NOT_FOUND(509,"user Not Found"),
    CREATE_FAILED(510,"create Failed"),
    CAR_NOT_EXIST(511,"car not Exist"),
    INSERT_FAILED(512,"insert Failed" ),
    //masterResult
    MASTERNAME_ERROR(520,"mastername Error"),
    MASTERNAME_USED(521,"master NameUsed"),
    QUERYINDEX_ERROR(522,"query Index Error"),
    LOW_PERMOSSIONS(523, "Insufficient permissions"),
    LEVEL_OUT_OF_BOUNDS(524, "Level Out of Bounds" ),
    VEHICLE_NOT_REGISTERED(525, "Vehicle Not Registered. Please Register Vehicle Before Generate Orders"),
    VEHICLE_ALREADY_PARKING(526,"Vehicle Already Parking" ),
    PARKINGLOT_NOT_FOUND(527,"parkinglot Not Found" ),
    ORDER_ERROR(528,"The order does not exist or has not been completed " );


    private Integer code;
    private String message;
    private ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
    public Integer getCode() {
        return code;
    }
    public String getMessage() {
        return message;
    }
}
