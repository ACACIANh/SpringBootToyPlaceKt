plugins {
    kotlin("plugin.jpa")
    kotlin("kapt")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.boot:spring-boot-starter-actuator")

//  embedded redis
    implementation("com.github.codemonstur:embedded-redis:1.0.0")
//  redisson
    implementation("org.redisson:redisson-spring-boot-starter:3.38.1")
//  local db
    implementation("com.h2database:h2")
//  kotest
    testImplementation("io.kotest:kotest-runner-junit5:5.5.5")
    testImplementation("io.kotest:kotest-assertions-core:5.5.5")
    testImplementation("io.kotest:kotest-property:5.5.5")
    testImplementation("io.kotest.extensions:kotest-extensions-spring:1.1.2")
}
