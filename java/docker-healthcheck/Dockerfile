FROM gcr.io/distroless/java:11
COPY target/*.jar app.jar
COPY HealthCheck.java .
EXPOSE 8080
CMD ["app.jar"]
HEALTHCHECK --interval=25s --timeout=3s --retries=2 CMD ["java", "HealthCheck.java", "||", "exit", "1"]
