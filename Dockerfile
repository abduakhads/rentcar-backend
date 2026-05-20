# Stage 1: Build the application
FROM openjdk:17-jdk-slim AS build
WORKDIR /app

# Copy gradle files
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

# Grant execute permission for gradlew and download dependencies
RUN chmod +x gradlew
RUN ./gradlew build -x test --no-daemon || return 0

# Copy source code and build the JAR
COPY src src
RUN ./gradlew bootJar --no-daemon

# Stage 2: Create the final production image
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy only the built JAR from the build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Expose the default Spring Boot port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
