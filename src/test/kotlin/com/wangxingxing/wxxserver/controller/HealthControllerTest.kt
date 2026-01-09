package com.wangxingxing.wxxserver.controller

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class HealthControllerTest {

    @Test
    fun `ping should return pong`() {
        val controller = HealthController()
        val res = controller.ping()
        assertEquals(0, res.code)
        assertEquals("pong", res.data)
    }
}
