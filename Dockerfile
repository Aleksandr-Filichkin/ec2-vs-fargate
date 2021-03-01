FROM amazoncorretto:11
VOLUME /tmp
ARG JAR_FILE=target/fargate.versus.ec2-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["sh", "-c", "java -Xms3048m -Xmx3048m -jar /app.jar"]
