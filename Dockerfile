FROM maven:3-jdk-8

RUN git clone https://github.com/smartheating/CommonsModule.git
WORKDIR /CommonsModule
RUN mvn clean install -DskipTests
RUN git clone https://github.com/smartheating/Repository.git /Repository
WORKDIR /Repository
RUN mvn clean install -DskipTests
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=docker","-jar","target/repository-0.0.1-SNAPSHOT.jar"]