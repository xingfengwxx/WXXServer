package com.wangxingxing.wxxserver.common.exception

/**
 * 统一错误码
 */
enum class ErrorCode(val code: Int, val defaultMessage: String) {
    // 1xxx 参数/校验错误
    PARAM_INVALID(1001, "参数校验失败"),
    PARAM_MISSING(1002, "必要参数缺失"),
    PARAM_TYPE_MISMATCH(1003, "参数类型错误"),

    // 2xxx 业务错误
    BIZ_CONFLICT(2001, "业务冲突"),
    NOT_FOUND(2004, "资源不存在"),
    RATE_LIMITED(2009, "请求过于频繁"),

    // 3xxx 鉴权相关
    UNAUTHORIZED(3001, "未认证"),
    FORBIDDEN(3003, "无权限"),

    // 4xxx 外部依赖/数据访问
    DB_ERROR(4001, "数据库错误"),
    EXTERNAL_SERVICE_ERROR(4002, "外部服务异常"),

    // 5xxx 系统错误
    INTERNAL_ERROR(5001, "系统异常"),
}
