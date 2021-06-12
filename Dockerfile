FROM openjdk:8-jre-alpine

RUN apk add --no-cache git
ARG app
ADD $app app.jar

CMD ["java", "-jar", "app.jar"]