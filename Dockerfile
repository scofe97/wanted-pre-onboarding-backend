FROM eclipse-temurin:17-jdk-alpine

ARG PROFILE
ARG JASYPT_ENCRYPTOR_PASSWORD

ENV PROFILE=${PROFILE}
ENV JASYPT_ENCRYPTOR_PASSWORD=${JASYPT_ENCRYPTOR_PASSWORD}

COPY build/libs/*.jar app.jar

ENTRYPOINT java -Dspring.profiles.active=${PROFILE} -Djasypt.encryptor.password=${JASYPT_ENCRYPTOR_PASSWORD} -jar /app.jar
