# ---- Stage 1: Build and Test the Spring Boot application ----
FROM maven:3.9.3-eclipse-temurin-17 as builder
WORKDIR /app

# Copy Maven project files
COPY pom.xml ./
COPY src ./src

# Run tests first before packaging
RUN mvn test && mvn clean package -DskipTests

# ---- Stage 2: Run the Spring Boot application ----
FROM eclipse-temurin:17-jdk
WORKDIR /app

# Copy the built JAR file from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# Expose the default Spring Boot port
EXPOSE 8080

# Run the Spring Boot application
CMD ["java", "-jar", "app.jar"]
