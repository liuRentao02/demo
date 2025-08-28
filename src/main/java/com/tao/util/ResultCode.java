package com.tao.util;

import lombok.Getter;

/**
 * ResultCode
 *
 * @author LiuRentao
 * @version 1.0
 * @since 2025/8/28 15:21
 */
@Getter
public enum ResultCode {
    SUCCESS(200,"成功"),
    FAIL(400,"失败"),
    ERROR(500,"出错");

    private final int code;
    private final String message;

    ResultCode(int i, String message) {
        this.code = i;
        this.message = message;
    }
}
