FROM adoptopenjdk:8-jre-alpine

ARG app
ADD $app app.jar

CMD ["java", "-jar", "app.jar"]