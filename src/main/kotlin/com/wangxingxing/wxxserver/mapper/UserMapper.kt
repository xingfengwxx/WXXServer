package com.wangxingxing.wxxserver.mapper

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.wangxingxing.wxxserver.domain.entity.User
import org.apache.ibatis.annotations.Mapper

@Mapper
interface UserMapper : BaseMapper<User>

