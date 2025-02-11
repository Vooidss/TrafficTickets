FROM openjdk:21-jdk
WORKDIR /authorication
COPY . .
WORKDIR /opt/authorication
ARG JAR_FILE=/target/*.jar
COPY ${JAR_FILE} /opt/authorication/authorication.jar
EXPOSE 8060
ENTRYPOINT ["java", "-jar", "/opt/authorication/authorication.jar"]