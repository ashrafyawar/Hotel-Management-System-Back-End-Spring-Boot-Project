FROM adoptopenjdk/openjdk15
EXPOSE 8080
ARG JAR_FILE=target/project-1.0-SNAPSHOT.jar
ADD ${JAR_FILE} hotelManagementSystemProject.jar
ENTRYPOINT ["java","-jar","/hotelManagementSystemProject.jar"]