FROM java:8-jre
MAINTAINER Weronika Majewska <weronikamajewska1@gmail.com>

ADD ./target/gateway.jar /app/
CMD ["java", "-Xmx512m", "-jar", "/app/gateway.jar"]

EXPOSE 8080