plugins {
    id("pokamon.library")
    id("pokamon.hilt")
    id("pokamon.library.network")
    id("pokamon.library.compose")
    id("pokamon.testing")
}

android {
    namespace = "com.pokamon.features.pokedex"
}

dependencies {
    implementation(project(":core:networking"))
    implementation(project(":core:design"))
}
