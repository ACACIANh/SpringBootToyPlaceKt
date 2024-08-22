package com.example.simplekotest

import io.kotest.core.spec.style.FunSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class EchoComponentTest(
    @Autowired val echoComponent: EchoComponent,
) : FunSpec({
    extensions(SpringExtension)

    test("echo") {
        val message = "Hello, World!"
        val result = echoComponent.echo(message)
        result shouldBe message
    }
})