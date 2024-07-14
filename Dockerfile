FROM eclipse-temurin:22-jdk-alpine
COPY target/SocialNetwork-0.0.1-SNAPSHOT.jar SocialNetwork-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "/SocialNetwork-0.0.1-SNAPSHOT.jar"]