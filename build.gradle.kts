plugins {
    id("java")
    id("me.champeau.jmh").version("0.7.2")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.openjdk.jmh:jmh-core:1.37")
    implementation("org.openjdk.jmh:jmh-generator-annprocess:1.37")

    testImplementation(platform("org.junit:junit-bom:5.10.3"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.compileJava {
    options.compilerArgs.add("--enable-preview")
}

java {
    sourceCompatibility = JavaVersion.VERSION_23
    targetCompatibility = JavaVersion.VERSION_23
}

tasks.test {
    useJUnitPlatform()
}

tasks.create<JavaExec>("run") {
    classpath = sourceSets.main.get().runtimeClasspath
    mainClass.set("org.example.Main")
    jvmArgs("--enable-preview")
}

tasks.compileJmhJava {
    options.compilerArgs.add("--enable-preview")
}

tasks.jmhCompileGeneratedClasses {
    options.compilerArgs.add("--enable-preview")
}

tasks.jmhRunBytecodeGenerator {
    jvmArgs.add("--enable-preview")
}

jmh {
    fork = 1
    timeOnIteration = "2s"

    jvmArgs = listOf("--enable-preview")
}
