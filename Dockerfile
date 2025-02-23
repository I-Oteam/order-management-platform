# 실행 단계: 실행 베이스 이미지 (빌드 단계는 이미 GitHub Actions에서 수행됨)
FROM openjdk:17-jre AS runtime

WORKDIR /app

COPY build/libs/order-management-platform-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java","-jar","app.jar"]