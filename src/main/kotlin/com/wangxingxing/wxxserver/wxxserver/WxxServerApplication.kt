package com.wangxingxing.wxxserver.wxxserver

import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["com.wangxingxing.wxxserver"])
@MapperScan("com.wangxingxing.wxxserver.mapper")
class WxxServerApplication

fun main(args: Array<String>) {
    runApplication<WxxServerApplication>(*args)
}
