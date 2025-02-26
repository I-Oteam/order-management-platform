FROM eclipse-temurin:17-jre AS runtime

ARG JAR_FILE=./build/libs/order-management-platform-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} /app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]