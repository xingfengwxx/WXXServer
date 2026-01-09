package com.wangxingxing.wxxserver.common.exception

/**
 * 业务异常，不要吞异常，应交给全局异常处理器处理
 */
class BusinessException(
    val errorCode: ErrorCode,
    override val message: String = errorCode.defaultMessage,
    val args: Map<String, Any?> = emptyMap(),
    cause: Throwable? = null
) : RuntimeException(message, cause)

