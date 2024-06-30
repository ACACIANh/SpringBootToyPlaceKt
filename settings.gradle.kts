rootProject.name = "SpringBootToyPlaceKt"

listOf(
    "AwsS3",
    "AwsSqs",
    "ExceptionStrategy",
    "Fcm",
    "SimpleKotest",
    "SimpleValidator",
    //"Something",
).forEach {
    include(it)
    project(":$it").projectDir = File("$rootDir/modules/$it")
}
