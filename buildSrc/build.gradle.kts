plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
    //gradlePluginPortal()
}

dependencies {
    compileOnly(gradleApi())
    implementation("com.android.tools.build:gradle:7.0.4")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.10")
    //implementation("com.github.ben-manes:gradle-versions-plugin:+")
}

gradlePlugin {
    plugins {
        register("base-android-plugin") {
            id = "base-android-convention"
            implementationClass = "BaseAndroidPlugin"
        }
        register("kotlin-kapt-plugin") {
            id = "kotlin-kapt-convention"
            implementationClass = "KotlinKaptPlugin"
        }
        register("kotlin-parcelize-plugin") {
            id = "kotlin-parcelize-convention"
            implementationClass = "KotlinParcelizePlugin"
        }
    }
    isAutomatedPublishing = false
}
