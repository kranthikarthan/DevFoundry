FROM eclipse-temurin:17-jre
WORKDIR /app
COPY target/springboot-kafka-example-0.1.0.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","/app/app.jar"]
