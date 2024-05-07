rootProject.name = "SpringBootToyPlaceKt"

listOf(
    "SimpleValidator",
    "AwsSqs",
    //"Something",
).forEach {
    include(it)
    project(":$it").projectDir = File("$rootDir/modules/$it")
}
