package com.wangxingxing.wxxserver.controller

import com.wangxingxing.wxxserver.common.model.Result
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/health")
@Validated
class HealthController {

    @GetMapping("/ping")
    fun ping(): Result<String> = Result.success("pong")
}

