FROM adoptopenjdk/maven-openjdk11

COPY target/bus-inventory-service-1.0.0.jar app.jar

EXPOSE 8073:8073

RUN apt-get update
RUN apt-get install -y gcc
RUN apt-get install -y curl
ENTRYPOINT ["java","-jar","app.jar"]