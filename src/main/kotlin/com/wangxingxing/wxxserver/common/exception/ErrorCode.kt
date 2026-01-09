package com.wangxingxing.wxxserver.common.exception

/**
 * 统一错误码
 */
enum class ErrorCode(val code: Int, val defaultMessage: String) {
    // 1xxx 参数/校验错误
    PARAM_INVALID(1001, "参数校验失败"),

    // 2xxx 业务错误
    BIZ_CONFLICT(2001, "业务冲突"),
    NOT_FOUND(2004, "资源不存在"),

    // 5xxx 系统错误
    INTERNAL_ERROR(5001, "系统异常"),
}

