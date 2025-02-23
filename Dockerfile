# 2단계: 실행 단계 (JRE 이미지 또는 JDK 이미지)
FROM eclipse-temurin:17-jre AS runtime

COPY --from=build /app.jar /app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]