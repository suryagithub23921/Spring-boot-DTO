FROM openjdk:11.0.10-jre

LABEL Author="Raghavendra Praveen Gopaluni <raghavendra.praveen.gopaluni@msg-global.com>"
LABEL app-version="1.1"

#Changing workdir to opt
WORKDIR /opt

##The application's JAR file
ARG JAR_FILE=target/oscare-idm*/*.jar

# Add the application's jar to the container
ADD ${JAR_FILE} oscare-idm-configuration.jar

# Make port  available to the world outside this container
EXPOSE 9292 

ENTRYPOINT ["java", "-jar", "oscare-idm-configuration.jar"]
