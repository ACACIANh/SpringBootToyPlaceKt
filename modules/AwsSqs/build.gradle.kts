dependencyManagement {
    imports {
        mavenBom("io.awspring.cloud:spring-cloud-aws:3.1.0")
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")

    implementation("io.awspring.cloud:spring-cloud-aws-starter")
    implementation("io.awspring.cloud:spring-cloud-aws-starter-sqs")
}