import java.io.ByteArrayOutputStream

plugins {
    id("pokamon.app")
    id("pokamon.hilt")
    id("pokamon.app.compose")
    id("pokamon.app.network")
}

android {
    namespace = "com.pokamon.demo"

    defaultConfig {
        applicationId = "com.pokamon.demo"
        var version: Int

        val bytes = ByteArrayOutputStream()
        project.exec {
            commandLine = "git rev-list HEAD --count".split(" ")
            standardOutput = bytes
        }

        val out = String(bytes.toByteArray()).trim().toInt() + 1
        version = out

        versionCode = version
        vectorDrawables {
            useSupportLibrary = true
        }

        buildTypes {
            debug {
                versionName = "0.0.$version"
                versionNameSuffix = "-debug"
                applicationIdSuffix = ".debug"
            }
        }
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.accompanist.ui.controller)
}
