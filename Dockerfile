# Etapa de build com Maven + JDK 17,
FROM maven:3.9.6-eclipse-temurin-17 AS builder

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests -Dcheckstyle.skip=true

# Etapa de runtime com JDK 17 mais leve,
FROM eclipse-temurin:17.0.12_7-jdk

WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]