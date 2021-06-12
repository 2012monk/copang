FROM openjdk:8-jre-alpine

ARG app
ADD $app app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]