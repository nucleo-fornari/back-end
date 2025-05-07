# Etapa de build com Maven + JDK 17
FROM maven:3.9.6-eclipse-temurin-17 AS builder

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

# Etapa de runtime com JDK 17
FROM eclipse-temurin:17.0.12_7-jdk

ENV MYSQL_HOST=IP_DO_BANCO
ENV EMAIL_SPRING=EMAIL_DO_SPRING
ENV SENHA_EMAIL_SPRING=SENHA_123
ENV SENHA_PADRAO=\$2a\$10\$H.GMLpeVWahKwhhf/2/L3ew33wL7fiGWcVQC6GXlKBLBIqRlk4bvm
ENV JWT_SECRET=8402962772ac1c2b09dadf3c52d844f6d79f45a66d7185a10a9e9cd692a68169
ENV SQL_PASSWORD=admin
ENV SQL_USER=root


WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]