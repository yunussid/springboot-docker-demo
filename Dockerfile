FROM eclipse-temurin:21

LABEL maintainer="yunus@gmail.com"

WORKDIR /app

COPY target/springboot-docker-demo.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]