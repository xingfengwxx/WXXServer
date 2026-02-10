package com.wangxingxing.wxxserver.dto

/**
 * 分页响应 VO
 */
data class PageResult<T>(
    val records: List<T>,
    val total: Long,
    val size: Long,
    val current: Long,
    val pages: Long
)

