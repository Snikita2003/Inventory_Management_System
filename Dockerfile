# -----------------------------
# Stage 1: Build the Spring Boot App
# -----------------------------
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copy only pom.xml first to leverage Docker cache
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy the source code
COPY src ./src

# Build the jar without running tests
RUN mvn clean package -DskipTests

# -----------------------------
# Stage 2: Run the Spring Boot App
# -----------------------------
FROM eclipse-temurin:17-jdk
WORKDIR /app

# Copy the jar from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose default port (Railway will assign dynamic PORT)
EXPOSE 8080

# Entry point
ENTRYPOINT ["sh", "-c", "java -jar app.jar --server.port=${PORT:-8080}"]
