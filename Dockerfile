FROM openjdk:8-jdk-alpine
EXPOSE 8089
ADD target/gestionSkiDevops.jar gestionSkiDevops.jar
ENTRYPOINT ["java","-jar","/gestionSkiDevops.jar"]