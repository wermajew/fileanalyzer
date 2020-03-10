FROM java:8-jre
MAINTAINER Weronika Majewska <weronikamajewska1@gmail.com>

ADD ./target/fileprocessor-0.0.1-SNAPSHOT.jar /app/
CMD ["java", "-Xmx1G", "-jar", "/app/fileprocessor-0.0.1-SNAPSHOT.jar"]

EXPOSE 8080