plugins {
    id("pokamon.library")
    id("pokamon.library.compose")
}

android {
    namespace = "com.pokamon.core.design"
}

dependencies {
    implementation(libs.material)
}
