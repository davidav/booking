FROM openjdk:17-oracle

ARG JAR_FILE=out/artifacts/booking_jar/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

