rootProject.name = "SpringBootToyPlaceKt"

listOf(
    "AnnotationTest",
    "AwsS3",
    "AwsSqs",
    "DistributedLock",
    "ExceptionStrategy",
    "Fcm",
    "InheritanceJpaJoined",
    "MemberService",
    "OpenCsv",
    "ScrollPagination",
    "SimpleKotest",
    "SimpleValidator",
    "SwaggerIsTest",
    "TransactionPropagation",
    // "Something",
).forEach {
    include(it)
    project(":$it").projectDir = File("$rootDir/modules/$it")
}
