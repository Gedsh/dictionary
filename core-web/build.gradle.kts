plugins {
    id("android-library-convention")
}

dependencies {
    implementation(Retrofit.retrofit)
    implementation(Retrofit.converter_gson)
}
