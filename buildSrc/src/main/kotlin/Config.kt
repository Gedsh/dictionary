import org.gradle.api.JavaVersion

object Config {
    const val application_id = "pan.alexander.dictionary"
    const val compile_sdk = 31
    const val min_sdk = 21
    const val target_sdk = 31
    val java_version = JavaVersion.VERSION_1_8
    const val kotlin_jvm = "1.8"
}
