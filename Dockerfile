FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY target/forum-0.0.1.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
