FROM adoptopenjdk/openjdk11:alpine-jre
VOLUME /tmp
ADD tournament-scheduling-latest.jar app.jar
RUN sh -c 'touch /app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
