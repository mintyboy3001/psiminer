group = rootProject.group
version = rootProject.version

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.0")
    implementation("io.github.vovak:astminer:0.6.4") {
        exclude("org.slf4j", "slf4j-simple")
    }

    implementation("org.jetbrains.kotlin:kotlin-reflect:1.5.0")
}

