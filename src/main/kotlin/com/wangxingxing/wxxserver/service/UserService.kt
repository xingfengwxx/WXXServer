package com.wangxingxing.wxxserver.service

import com.wangxingxing.wxxserver.domain.entity.User
import com.wangxingxing.wxxserver.dto.PageResult

interface UserService {
    fun getById(id: Long): User?
    fun create(user: User): Long
    fun getPage(current: Long, size: Long): PageResult<User>
}

