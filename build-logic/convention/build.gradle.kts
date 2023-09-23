plugins {
    `kotlin-dsl`
}
group = "com.pokamon.buildlogic"

java {
    java {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    dependencies {
        compileOnly(libs.android.gradle)
        compileOnly(libs.kotlin.gradle)
        compileOnly(libs.ksp.gradlePlugin)
    }

}

gradlePlugin {
    plugins {
        register("androidApp"){
            id = "pokamon.app"
            implementationClass = "AndroidApplicationConventionPlugin"
        }

        register("androidLibrary"){
            id = "pokamon.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }

        register("androidHilt") {
            id = "pokamon.hilt"
            implementationClass = "AndroidHiltConventionPlugin"
        }

        register("androidApplicationCompose") {
            id = "pokamon.app.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }

        register("androidApplicationNetwork") {
            id = "pokamon.app.network"
            implementationClass = "AndroidApplicationNetworkConventionPlugin"
        }

        register("androidLibraryNetwork") {
            id = "pokamon.library.network"
            implementationClass = "AndroidLibraryNetworkConventionPlugin"
        }

        register("androidLibraryCompose") {
            id = "pokamon.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
        register("androidTesting") {
            id = "pokamon.testing"
            implementationClass = "TestingConventionPlugin"
        }

    }
}