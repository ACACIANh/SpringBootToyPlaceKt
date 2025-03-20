rootProject.name = "SpringBootToyPlaceKt"

listOf(
    "AwsS3",
    "AwsSqs",
    "DistributedLock",
    "ExceptionStrategy",
    "Fcm",
    "InheritanceJpaJoined",
    "OpenCsv",
    "SimpleKotest",
    "SimpleValidator",
    // "Something",
).forEach {
    include(it)
    project(":$it").projectDir = File("$rootDir/modules/$it")
}
