dependencies {

    //version compatibility check
    testImplementation("io.kotest:kotest-runner-junit5:5.5.5")
    testImplementation("io.kotest:kotest-assertions-core:5.5.5")
    testImplementation("io.kotest:kotest-property:5.5.5")
    testImplementation("io.kotest.extensions:kotest-extensions-spring:1.1.2")

    testImplementation("io.mockk:mockk:1.13.4")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
