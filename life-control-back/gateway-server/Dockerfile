FROM openjdk:22
VOLUME [ "/tmp" ]
EXPOSE 8090
ADD ./target/gateway-server-1.0.0.jar gateway-server.jar
ENTRYPOINT [ "java", "-jar", "/gateway-server.jar" ]