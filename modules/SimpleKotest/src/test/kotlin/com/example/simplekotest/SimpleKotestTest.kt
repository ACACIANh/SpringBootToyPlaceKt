package com.example.simplekotest

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.ints.shouldBePositive
import io.kotest.matchers.shouldBe
import mu.KotlinLogging

private val log = KotlinLogging.logger { }

class SimpleKotestTest : StringSpec({

    beforeTest {
        log.info("Starting a test $it")
    }
    afterTest { (test, result) ->
        log.info("Finished spec with result $result")
    }

    "simple test" {
        val sum = 1 + 1
        sum.shouldBePositive()
//        sum.shouldBeNegative()
        sum shouldBe 2
    }
})