plugins {
    kotlin("plugin.jpa")
    kotlin("kapt")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

//  local db
    implementation("com.h2database:h2")
}
