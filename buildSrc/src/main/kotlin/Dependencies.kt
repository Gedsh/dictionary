object Design {
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val legacy = "androidx.legacy:legacy-support-v4:${Versions.legacy}"
    const val constraint = "androidx.constraintlayout:constraintlayout:${Versions.constraint}"
    const val material = "com.google.android.material:material:${Versions.material}"
}

object Kotlin {
    const val core = "androidx.core:core-ktx:${Versions.core}"
    const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.stdlib}"
    const val coroutines_android =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines_android}"
    const val lifecycle_viewmodel =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle_viewmodel}"
    const val lifecycle_runtime =
        "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle_runtime}"
}

object Retrofit {
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val converter_gson = "com.squareup.retrofit2:converter-gson:${Versions.converter_gson}"
    const val logging_interceptor =
        "com.squareup.okhttp3:logging-interceptor:${Versions.interceptor}"
}

object Koin {
    const val koin_core = "io.insert-koin:koin-core:${Versions.koinCore}"
    const val koin_android = "io.insert-koin:koin-android:${Versions.koinAndroid}"
}

object Coil {
    const val coil = "io.coil-kt:coil:${Versions.coil}"
    const val coil_base = "io.coil-kt:coil-base:${Versions.coil_base}"
}

object Room {
    const val runtime = "androidx.room:room-runtime:${Versions.runtime}"
    const val compiler = "androidx.room:room-compiler:${Versions.room_compiler}"
    const val room_ktx = "androidx.room:room-ktx:${Versions.room_ktx}"
}

object ViewBinding {
    const val binding =
        "com.github.kirich1409:viewbindingpropertydelegate-noreflection:${Versions.binding}"
}

object RecycleView {
    const val adapter_delegates =
        "com.hannesdorfmann:adapterdelegates4-kotlin-dsl:${Versions.adapter_delegates}"
    const val adapter_delegates_viewbinding =
        "com.hannesdorfmann:adapterdelegates4-kotlin-dsl-viewbinding:${Versions.adapter_delegates}"
}

object TestImpl {
    const val junit = "junit:junit:${Versions.junit}"
    const val core_testing = "androidx.arch.core:core-testing:${Versions.core_testing}"
    const val ext = "androidx.test.ext:junit:${Versions.ext_junit}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso_core}"
    const val mockito_inline = "org.mockito:mockito-inline:${Versions.mockito_inline}"
    const val mockito_android = "org.mockito:mockito-android:${Versions.mockito_android}"
    const val coroutines_test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines_test}"
    const val koin_test = "io.insert-koin:koin-test:${Versions.koinCore}"
    const val koin_test_junit = "io.insert-koin:koin-test-junit4:${Versions.koinCore}"
    const val mockito_kotlin = "org.mockito.kotlin:mockito-kotlin:${Versions.mockito_kotlin}"
}
