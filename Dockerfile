FROM openjdk:21-jdk-slim as build
WORKDIR /app
COPY . .
RUN apt-get update && apt-get install -y maven
RUN mvn clean package -DskipTests
FROM openjdk:21-jre-slim
WORKDIR /app
COPY --from=build /app/target/darpv2-0.0.1-SNAPSHOT.jar /app/darpv2-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "darpv2-0.0.1-SNAPSHOT.jar "]