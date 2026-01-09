package com.wangxingxing.wxxserver.common.handler

import com.wangxingxing.wxxserver.common.exception.BusinessException
import com.wangxingxing.wxxserver.common.exception.ErrorCode
import com.wangxingxing.wxxserver.common.model.Result
import jakarta.validation.ConstraintViolationException
import org.springframework.dao.DataAccessException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

/**
 * 全局异常处理
 */
@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException::class)
    fun handleBusiness(ex: BusinessException): ResponseEntity<Result<Nothing>> {
        val status = when (ex.errorCode) {
            ErrorCode.PARAM_INVALID, ErrorCode.PARAM_MISSING, ErrorCode.PARAM_TYPE_MISMATCH -> HttpStatus.BAD_REQUEST
            ErrorCode.BIZ_CONFLICT -> HttpStatus.CONFLICT
            ErrorCode.NOT_FOUND -> HttpStatus.NOT_FOUND
            ErrorCode.RATE_LIMITED -> HttpStatus.TOO_MANY_REQUESTS
            ErrorCode.DB_ERROR, ErrorCode.EXTERNAL_SERVICE_ERROR, ErrorCode.INTERNAL_ERROR -> HttpStatus.INTERNAL_SERVER_ERROR
            else -> HttpStatus.BAD_REQUEST
        }
        val body = Result.failure<Nothing>(ex.errorCode.code, ex.message ?: ex.errorCode.defaultMessage)
        return ResponseEntity.status(status).body(body)
    }

    @ExceptionHandler(
        MethodArgumentNotValidException::class,
        BindException::class,
        ConstraintViolationException::class
    )
    fun handleValidation(ex: Exception): ResponseEntity<Result<Nothing>> {
        val body = Result.failure<Nothing>(ErrorCode.PARAM_INVALID.code, ex.message ?: ErrorCode.PARAM_INVALID.defaultMessage)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body)
    }

    @ExceptionHandler(MissingServletRequestParameterException::class)
    fun handleParamMissing(ex: MissingServletRequestParameterException): ResponseEntity<Result<Nothing>> =
        ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Result.failure(ErrorCode.PARAM_MISSING.code, ex.message ?: ErrorCode.PARAM_MISSING.defaultMessage))

    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    fun handleTypeMismatch(ex: MethodArgumentTypeMismatchException): ResponseEntity<Result<Nothing>> =
        ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Result.failure(ErrorCode.PARAM_TYPE_MISMATCH.code, ex.message ?: ErrorCode.PARAM_TYPE_MISMATCH.defaultMessage))

    @ExceptionHandler(DataAccessException::class)
    fun handleDataAccess(ex: DataAccessException): ResponseEntity<Result<Nothing>> =
        ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.failure(ErrorCode.DB_ERROR.code, ex.message ?: ErrorCode.DB_ERROR.defaultMessage))

    @ExceptionHandler(Exception::class)
    fun handleOther(ex: Exception): ResponseEntity<Result<Nothing>> =
        ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.failure(ErrorCode.INTERNAL_ERROR.code, ex.message ?: ErrorCode.INTERNAL_ERROR.defaultMessage))
}
