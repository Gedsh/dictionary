plugins {
    id("android-library-convention")
}

dependencies {
    //Kotlin
    implementation(Kotlin.core)
    implementation(Kotlin.stdlib)

    //AndroidX
    implementation(Design.appcompat)
    implementation(Design.legacy)
    implementation(Design.constraint)

    //Design
    implementation(Design.material)
}
