# backend-java/Dockerfile

# 1段階目：ビルド用のイメージ（Maven + JDK）
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

# pom.xml とソースコードをコピー
COPY pom.xml .
COPY src ./src

# 単体テストは一旦スキップしてjarを作る
RUN mvn clean package -DskipTests

# 2段階目：実行用の軽量イメージ（JREのみ）
FROM eclipse-temurin:21-jre
WORKDIR /app

# ビルド成果物（jarファイル）だけをコピー
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

# Spring Boot を起動
ENTRYPOINT ["java", "-jar", "app.jar"]