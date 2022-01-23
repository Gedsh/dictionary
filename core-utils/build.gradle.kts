plugins {
    id("android-library-convention")
}

android {
    defaultConfig {
        buildConfigField(
            "String",
            "API_BASE_URL",
            "\"https://dictionary.skyeng.ru/api/public/v1/\""
        )
    }
}

dependencies {
    //Coroutines
    implementation(Kotlin.coroutines_android)
    implementation(TestImpl.coroutines_test)

    //Test
    testImplementation(TestImpl.junit)
    androidTestImplementation(TestImpl.ext)
}
