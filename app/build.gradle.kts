plugins {
    id("android-application-convention")
    id("kotlin-parcelize-convention")
    //id("com.github.ben-manes.versions")
}

android {

    defaultConfig {
        applicationId = Config.application_id
        versionCode = Releases.version_code
        versionName = Releases.version_name
    }

}

dependencies {

    implementation(projects.coreDb)
    implementation(projects.coreUtils)
    implementation(projects.coreWeb)
    implementation(projects.coreUi)

    //Kotlin
    implementation(Kotlin.core)
    implementation(Kotlin.stdlib)

    //AndroidX
    implementation(Design.appcompat)
    implementation(Design.legacy)
    implementation(Design.constraint)

    //Design
    implementation(Design.material)

    //DI
    implementation(Koin.koin_core)
    implementation(Koin.koin_android)

    //Coroutines
    implementation(Kotlin.coroutines_android)
    implementation(Kotlin.lifecycle_viewmodel)
    implementation(Kotlin.lifecycle_runtime)

    //Retrofit + Okhttp + Gson
    implementation(Retrofit.retrofit)
    implementation(Retrofit.converter_gson)
    implementation(Retrofit.logging_interceptor)

    //Room
    implementation(Room.runtime)
    implementation(Room.room_ktx)

    //Image Loading
    implementation(Coil.coil)
    implementation(Coil.coil_base)

    //ViewBinding
    implementation(ViewBinding.binding)

    //Test
    testImplementation(TestImpl.junit)
    androidTestImplementation(TestImpl.ext)
    androidTestImplementation(TestImpl.espresso)

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
}
