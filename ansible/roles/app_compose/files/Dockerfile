FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY app.jar /app/app.jar

EXPOSE 8080

#ENV SPRING_PROFILES_ACTIVE=prod

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
