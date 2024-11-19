package com.chy.utils;

/**
 * 统一返回结果状态信息类
 *
 */
public enum ResultCodeEnum {

    SUCCESS(200,"success"),
//    NOTLOGIN(501,"notLogin"),
    UNAUTHROIZED(501,"Unauthorized"),
    UPDATE_FIELD(502,"Feild to update Information"),
    USERNAME_ERROR(503,"usernameError"),
    PASSWORD_ERROR(504,"passwordError"),
    USERNAME_USED(505,"userNameUsed"),
    //masterResult
    MASTERNAME_ERROR(520,"masternameError"),
    MASTERNAME_USED(521,"masterNameUsed"),
    QUERYINDEX_ERROR(522,"queryIndexError");

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
