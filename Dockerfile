# Stage 1: Build with JDK 25.
FROM eclipse-temurin:25-jdk-alpine AS build

WORKDIR /app

COPY .mvn/ .mvn/
COPY mvnw pom.xml ./

# Fix Windows line endings and allow wrapper execution.
RUN tr -d '\r' < mvnw > mvnw.tmp \
    && mv mvnw.tmp mvnw \
    && chmod +x mvnw

RUN ./mvnw dependency:go-offline -B

COPY src/ src/

RUN ./mvnw -B -DskipTests package

# Stage 2: Run with JRE 25.
FROM eclipse-temurin:25-jre-alpine

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75", "-jar", "app.jar"]