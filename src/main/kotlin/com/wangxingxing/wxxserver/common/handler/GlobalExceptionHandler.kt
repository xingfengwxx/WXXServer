package com.wangxingxing.wxxserver.common.handler

import com.wangxingxing.wxxserver.common.exception.BusinessException
import com.wangxingxing.wxxserver.common.exception.ErrorCode
import com.wangxingxing.wxxserver.common.model.Result
import jakarta.validation.ConstraintViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

/**
 * 全局异常处理
 */
@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException::class)
    fun handleBusiness(ex: BusinessException): ResponseEntity<Result<Nothing>> {
        val body = Result.failure<Nothing>(ex.errorCode.code, ex.message)
        val status = when (ex.errorCode) {
            ErrorCode.PARAM_INVALID -> HttpStatus.BAD_REQUEST
            ErrorCode.BIZ_CONFLICT -> HttpStatus.CONFLICT
            ErrorCode.NOT_FOUND -> HttpStatus.NOT_FOUND
            else -> HttpStatus.BAD_REQUEST
        }
        return ResponseEntity.status(status).body(body)
    }

    @ExceptionHandler(
        MethodArgumentNotValidException::class,
        BindException::class,
        ConstraintViolationException::class
    )
    fun handleValidation(ex: Exception): ResponseEntity<Result<Nothing>> {
        val body = Result.failure<Nothing>(ErrorCode.PARAM_INVALID.code, ex.message ?: "参数校验失败")
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body)
    }

    @ExceptionHandler(Exception::class)
    fun handleOther(ex: Exception): ResponseEntity<Result<Nothing>> {
        val body = Result.failure<Nothing>(ErrorCode.INTERNAL_ERROR.code, ex.message ?: ErrorCode.INTERNAL_ERROR.defaultMessage)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body)
    }
}

