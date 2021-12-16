plugins {
    id("android-library-convention")
    id("kotlin-kapt-convention")
    id("kotlin-parcelize-convention")
}

dependencies {
    implementation(projects.coreUtils)
    implementation(Room.runtime)
    implementation(Room.room_ktx)
}
