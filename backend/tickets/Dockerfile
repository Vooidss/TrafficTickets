FROM openjdk:21-jdk
WORKDIR /tickets
COPY . .
WORKDIR /opt/tickets
ARG JAR_FILE=/target/*.jar
COPY ${JAR_FILE} /opt/tickets/tickets.jar
EXPOSE 8091
ENTRYPOINT ["java", "-jar", "/opt/tickets/tickets.jar"]