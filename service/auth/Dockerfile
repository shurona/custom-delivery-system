# Step 1: Base image 설정 (예: OpenJDK 17)
FROM openjdk:17-jdk-slim

# Step 2: 작업 디렉토리 설정
WORKDIR /app

# Step 3: Gradle 빌드를 수행하여 dependencies 포함
COPY ./build/libs/auth-0.0.1-SNAPSHOT.jar /app/auth.jar

# Step 4: 실행 명령어
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=dev", "/app/auth.jar"]