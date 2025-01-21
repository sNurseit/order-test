FROM bellsoft/liberica-openjdk-alpine-musl
COPY ./target/order-project-0.0.1-SNAPSHOT.jar .
CMD ["java","-jar","order-project-0.0.1-SNAPSHOT.jar"]