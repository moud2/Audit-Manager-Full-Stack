# Dockerfile for java spring server
FROM bellsoft/liberica-openjdk-alpine:21

WORKDIR /app

COPY build/libs/insight-spring.jar /app/insight-spring.jar

EXPOSE 81

ENTRYPOINT ["java", "-jar", "insight-spring.jar"]

ENV SERVER_PORT 81