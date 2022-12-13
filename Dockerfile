
FROM amazoncorretto:11-alpine-jdk
EXPOSE 8080
COPY target/Cobefy-0.0.1-SNAPSHOT.jar /Cobefy-0.0.1-SNAPSHOT
CMD ["java", "-jar", "/Cobefy-0.0.1-SNAPSHOT"]