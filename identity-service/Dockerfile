# ============================================================
# STAGE 1: Build – dùng Maven + JDK 21 để compile và đóng gói
# ============================================================
FROM maven:3.9.6-eclipse-temurin-21-alpine AS build
WORKDIR /app

# Copy pom.xml trước để tận dụng Docker layer cache cho dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -q

# Copy source code và build
COPY src ./src
RUN mvn clean package -DskipTests -q

# ============================================================
# STAGE 2: Runtime – chỉ chứa JRE nhẹ và file JAR đã build
# ============================================================
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java", "-Xmx256m", "-Xms256m", "-jar", "app.jar"]