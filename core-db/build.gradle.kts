plugins {
    id("android-library-convention")
    id("kotlin-kapt-convention")
    id("kotlin-parcelize-convention")
}

dependencies {

    implementation(projects.coreUtils)

    //Room
    implementation(Room.runtime)
    implementation(Room.room_ktx)

    //Test
    testImplementation(TestImpl.junit)
    testImplementation(TestImpl.mockito_inline)
    testImplementation (TestImpl.mockito_kotlin)
    testImplementation(TestImpl.robolectric)
    testImplementation(TestImpl.ext)
    testImplementation(TestImpl.core_testing)
    testImplementation(TestImpl.coroutines_test)
    testImplementation(Design.appcompat)
}
