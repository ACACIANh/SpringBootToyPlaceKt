rootProject.name = "SpringBootToyPlaceKt"

listOf(
    "AwsSqs",
    "ExceptionStrategy",
    "Fcm",
    "SimpleValidator",
    //"Something",
).forEach {
    include(it)
    project(":$it").projectDir = File("$rootDir/modules/$it")
}
