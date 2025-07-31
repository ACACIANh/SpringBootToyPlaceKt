package com.example.annotationtest

import java.lang.annotation.Inherited

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Inherited
annotation class CustomAnnotation(
    val value: String = "",
    val priority: Int = 0,
    val enabled: Boolean = true
)
