FROM maven:4.0.0-rc-5-sapmachine-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21.0.10_7-jre-ubi9-minimal
WORKDIR /app
EXPOSE 420
COPY --from=build /app/target/*.jar app.jar
CMD ["java", "-jar", "app.jar"]
