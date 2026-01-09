package com.wangxingxing.wxxserver.service.impl

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.wangxingxing.wxxserver.common.exception.BusinessException
import com.wangxingxing.wxxserver.common.exception.ErrorCode
import com.wangxingxing.wxxserver.domain.entity.User
import com.wangxingxing.wxxserver.mapper.UserMapper
import com.wangxingxing.wxxserver.service.UserService
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserServiceImpl(
    private val userMapper: UserMapper
) : UserService {

    @Cacheable(cacheNames = ["user"], key = "#id")
    override fun getById(id: Long): User? {
        return userMapper.selectById(id)
    }

    @Transactional
    override fun create(user: User): Long {
        val exists = userMapper.selectOne(QueryWrapper<User>().eq("username", user.username))
        if (exists != null) throw BusinessException(ErrorCode.BIZ_CONFLICT, "用户名已存在")
        userMapper.insert(user)
        return user.id ?: throw BusinessException(ErrorCode.INTERNAL_ERROR, "创建用户失败")
    }
}

