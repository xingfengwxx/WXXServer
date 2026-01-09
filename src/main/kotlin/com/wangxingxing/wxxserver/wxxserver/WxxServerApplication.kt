package com.wangxingxing.wxxserver.wxxserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["com.wangxingxing.wxxserver"])
class WxxServerApplication

fun main(args: Array<String>) {
    runApplication<WxxServerApplication>(*args)
}
