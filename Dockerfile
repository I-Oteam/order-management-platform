FROM openjdk:17-jdk AS build

ARG JAR_FILE=./build/libs/order-management-platform-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

FROM eclipse-temurin:17-jre AS runtime

COPY --from=build /app.jar /app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]