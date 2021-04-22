FROM openjdk:8-jre-slim
WORKDIR /app
COPY target/service-0.0.1-SNAPSHOT.jar /app/service.jar

# wait-for-it
#COPY wait-for-it.sh /app/wait-for-it.sh
#RUN chmod +x wait-for-it.sh
#CMD ["./wait-for-it.sh", "mysql:3306", "--", "java", "-jar", "service.jar"]

ENTRYPOINT ["java", "-jar", "service.jar"]