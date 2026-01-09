package com.wangxingxing.wxxserver.controller

import com.wangxingxing.wxxserver.common.model.Result
import com.wangxingxing.wxxserver.domain.entity.User
import com.wangxingxing.wxxserver.dto.UserCreateRequest
import com.wangxingxing.wxxserver.service.UserService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserController(
    private val userService: UserService
) {

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): Result<User?> = Result.success(userService.getById(id))

    @PostMapping
    fun create(@Valid @RequestBody req: UserCreateRequest): Result<Long> {
        val id = userService.create(User(username = req.username, email = req.email))
        return Result.success(id)
    }
}

