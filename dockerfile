FROM openjdk:17
ARG JAR_FILE=./target/DevCampUs-0.0.1-SNAPSHOT.war
COPY ${JAR_FILE} DevCampUs-0.0.1-SNAPSHOT.war
EXPOSE 8080
ENTRYPOINT ["java","-jar","DevCampUs-0.0.1-SNAPSHOT.war"]