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
    androidTestImplementation(TestImpl.ext)
}
