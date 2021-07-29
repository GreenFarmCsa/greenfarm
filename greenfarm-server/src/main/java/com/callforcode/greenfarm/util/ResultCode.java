package com.callforcode.greenfarm.util;

public enum ResultCode {
    OK("00"), ERROR("01"), UNAUTHORIZED("02"), UNAUTHENTICATED("03"), FAULT("09");

    private String code;

    ResultCode(String code) {
        this.code = code;
    }
}
