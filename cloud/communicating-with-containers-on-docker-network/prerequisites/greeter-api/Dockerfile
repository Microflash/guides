FROM maven:3.6.1-jdk-13-alpine as builder
WORKDIR /usr/home/greeter
COPY . .
RUN mvn package

FROM adoptopenjdk/openjdk13-openj9:jre-13.0.2_8_openj9-0.18.0-alpine
COPY --from=builder /usr/home/greeter/target/greeter-api-*.jar greeter-api.jar
EXPOSE 8084
CMD ["java", "-jar", "greeter-api.jar"]
