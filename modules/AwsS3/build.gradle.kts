dependencyManagement {
    imports {
        mavenBom("io.awspring.cloud:spring-cloud-aws:3.1.0")
    }
}

dependencies {
//    implementation("com.amazonaws:aws-java-sdk-s3:1.12.174")
    implementation("io.awspring.cloud:spring-cloud-aws-starter")
    implementation("io.awspring.cloud:spring-cloud-aws-starter-s3")

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-test")
}
