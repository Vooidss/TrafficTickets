FROM openjdk:21-jdk
WORKDIR /discovery
COPY . .
WORKDIR /opt/discovery
ARG JAR_FILE=/target/*.jar
COPY ${JAR_FILE} /opt/discovery/discovery.jar
EXPOSE 8761
ENTRYPOINT ["java", "-jar", "/opt/discovery/discovery.jar"]