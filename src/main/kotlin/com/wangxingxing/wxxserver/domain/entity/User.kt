package com.wangxingxing.wxxserver.domain.entity

import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import com.baomidou.mybatisplus.annotation.IdType

@TableName("user")
data class User(
    @TableId(type = IdType.AUTO)
    val id: Long? = null,
    val username: String,
    val email: String
)

