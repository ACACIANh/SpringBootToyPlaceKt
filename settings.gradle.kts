rootProject.name = "SpringBootToyPlaceKt"

listOf(
    "SimpleValidator",
    //"Something",
).forEach {
    include(it)
    project(":$it").projectDir = File("$rootDir/modules/$it")
}
