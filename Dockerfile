FROM amazoncorretto:17
VOLUME /tmp
ARG JAR_FILE=target/fargate.versus.ec2-0.0.1-SNAPSHOT.jar
EXPOSE 8080
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["sh", "-c", "java -Xms700m -Xmx700m -jar /app.jar"]
