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
    implementation(Kotlin.coroutines_android)
}
