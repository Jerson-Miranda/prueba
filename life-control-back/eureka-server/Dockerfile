FROM openjdk:22
VOLUME [ "/tmp" ]
EXPOSE 8761
ADD ./target/eureka-server-1.0.0.jar eureka-server.jar
ENTRYPOINT [ "java", "-jar", "/eureka-server.jar" ]