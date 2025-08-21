package com.example.swaggeristest

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.cfg.MapperConfig
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod
import mu.KotlinLogging
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

private val log = KotlinLogging.logger { }

@Configuration
class JacksonConfiguration {

    /**
     * 해당 옵션을 설정해도 example 로 나오는 isXXX 프로퍼티는 XXX 로 표현된다 ㅠㅠ
     */
    @Bean
    fun jacksonCustomizer(): Jackson2ObjectMapperBuilderCustomizer {
        return Jackson2ObjectMapperBuilderCustomizer { builder ->
            builder.propertyNamingStrategy(
                object : PropertyNamingStrategies.LowerCamelCaseStrategy() {
                    override fun nameForGetterMethod(
                        config: MapperConfig<*>?,
                        method: AnnotatedMethod?,
                        defaultName: String?,
                    ): String {
                        log.info { "method name: ${method?.name}" }
                        // is로 시작하는 boolean getter는 그대로 유지
                        if (method?.name?.startsWith("is") == true && method.rawReturnType == Boolean::class.java) {
                            return method.name
                        }
                        return super.nameForGetterMethod(config, method, defaultName)
                    }
                },
            )
        }
    }
}
