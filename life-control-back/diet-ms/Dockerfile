FROM openjdk:22
VOLUME [ "/tmp" ]
ADD ./target/diet-ms-1.0.0.jar diet-ms.jar
ENTRYPOINT [ "java", "-jar", "/diet-ms.jar" ]