package com.wangxingxing.wxxserver.common.model

import com.fasterxml.jackson.annotation.JsonInclude

/**
 * 统一响应包装
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
data class Result<T>(
    val code: Int,
    val message: String,
    val data: T? = null,
    val traceId: String? = null
) {
    companion object {
        fun <T> success(data: T? = null, message: String = "OK", traceId: String? = null): Result<T> =
            Result(code = 0, message = message, data = data, traceId = traceId)

        fun <T> failure(code: Int, message: String, traceId: String? = null, data: T? = null): Result<T> =
            Result(code = code, message = message, data = data, traceId = traceId)
    }
}

