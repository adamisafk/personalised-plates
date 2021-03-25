FROM ecr.tooling.dvla.gov.uk/base-images/openjre:8u191-jre

WORKDIR /app
COPY service/target/service-0.0.1-SNAPSHOT.jar /app/service.jar
ENTRYPOINT exec java -jar service.jar