import java.util.Properties




plugins {
    id("java")
    id("jacoco")
    id("com.github.spacialcircumstances.gradle-cucumber-reporting") version "0.1.25"
    id("org.sonarqube") version "4.4.1.3373"
    id("maven-publish")

}

group = "com.example"
version = "1.0-SNAPSHOT"



tasks.test {
    finalizedBy("jacocoTestReport")
    finalizedBy("sonar")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "NouadjiNazim"
            artifactId = "lib"
            version = "1.0"

            from(components["java"])
        }
    }
    repositories {
        maven {
            url = uri("https://mymavenrepo.com/repo/cEmjfkxugPlzLxXg1A2B/")
            credentials {
                username = "myMavenRepo"
                password = "test0005"
            }
        }

    }
}

sonar {
    properties {
        property("sonar.projectKey", "Tp_Jenkins")
        property("sonar.projectName", "My Gradle Project_Jenkins")
        property("sonar.host.url", "http://localhost:9000")
        property("sonar.login", "sqa_9d7dfca1275ac35f2620073c6efdeeec6b7808cb")
    }
}




dependencies {
    testImplementation ("io.cucumber:cucumber-java:6.0.0")
    testImplementation ("io.cucumber:cucumber-junit:6.0.0")
    testImplementation ("junit:junit:4.12")
}





