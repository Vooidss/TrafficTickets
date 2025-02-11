FROM openjdk:21-jdk
WORKDIR /config-server
COPY . .
WORKDIR /opt/config-server
ARG JAR_FILE=/target/*.jar
COPY ${JAR_FILE} /opt/config-server/config-server.jar
EXPOSE 8888
ENTRYPOINT ["java", "-jar", "/opt/config-server/config-server.jar"]