package com.wangxingxing.wxxserver.service

import com.wangxingxing.wxxserver.domain.entity.User

interface UserService {
    fun getById(id: Long): User?
    fun create(user: User): Long
}

