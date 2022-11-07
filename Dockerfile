FROM openjdk:11
VOLUME /tmp
EXPOSE 9042
ADD ./target/ms-operations-0.0.1-SNAPSHOT.jar ms-operations.jar
ENTRYPOINT ["java","-jar","/ms-operations.jar"]