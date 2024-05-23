plugins {
	java
	jacoco
	id("org.springframework.boot") version "3.2.0"
	id("io.spring.dependency-management") version "1.1.4"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation ("org.springframework.boot:spring-boot-starter-validation")
	compileOnly("org.projectlombok:lombok")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	runtimeOnly("org.postgresql:postgresql")
	annotationProcessor("org.projectlombok:lombok")
	implementation("org.mapstruct:mapstruct:1.5.5.Final")
	// https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind
	implementation("com.fasterxml.jackson.core:jackson-databind:2.16.1")
	implementation ("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
	implementation ("org.springframework.boot:spring-boot-starter-security")
	implementation("io.jsonwebtoken:jjwt:0.9.1")
	implementation ("org.springframework.boot:spring-boot-starter-data-redis")
	implementation("redis.clients:jedis")
	annotationProcessor("org.mapstruct:mapstruct-processor:1.5.5.Final")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
//	testImplementation("org.testcontainers:testcontainers:1.19.8")
//	testImplementation("org.testcontainers:junit-jupiter:1.19.8")
//	testImplementation("org.springframework.boot:spring-boot-testcontainers:3.1.3")
	testImplementation ("org.testcontainers:postgresql")
	testImplementation ("org.springframework.security:spring-security-test")
	implementation("javax.xml.bind:jaxb-api:2.3.1")
}
tasks.test{
	useJUnitPlatform()
}

jacoco {
	toolVersion = "0.8.7" // Укажите желаемую версию JaCoCo
}

tasks.jacocoTestReport {
	dependsOn("test") // Указывает, что отчет зависит от выполнения тестов
	reports {
		xml.required.set(true)
		html.required.set(true)
	}
}

tasks.test {
	// Интеграция JaCoCo с тестированием
	finalizedBy("jacocoTestReport") // Генерирует отчет сразу после выполнения тестов
}



