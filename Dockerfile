FROM openjdk:8-jdk-alpine
ADD ./docker-demo-book.jar docker-demo-book.jar
ENTRYPOINT ["java", "-jar", "docker-demo-book.jar"]
