package com.wangxingxing.wxxserver.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class UserCreateRequest(
    @field:NotBlank(message = "用户名不能为空")
    @field:Size(min = 3, max = 32, message = "用户名长度 3-32")
    val username: String,

    @field:NotBlank(message = "邮箱不能为空")
    @field:Email(message = "邮箱格式不正确")
    val email: String
)

