FROM java:8-jdk-alpine
VOLUME /tmp
EXPOSE 9011
ARG JAR_FILE=target/repository-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} repository.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=docker","-jar","/repository.jar"]