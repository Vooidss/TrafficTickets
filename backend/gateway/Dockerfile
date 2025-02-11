FROM openjdk:21-jdk
WORKDIR /gateway
COPY . .
WORKDIR /opt/gateway
ARG JAR_FILE=/target/*.jar
COPY ${JAR_FILE} /opt/gateway/gateway.jar
EXPOSE 8091
ENTRYPOINT ["java", "-jar", "/opt/gateway/gateway.jar"]