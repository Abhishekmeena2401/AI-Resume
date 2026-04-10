# Build stage
FROM maven:3.8.5-openjdk-17 AS build
COPY . .
# Fix permissions for the maven wrapper
RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

# Run stage
# We use eclipse-temurin because the old openjdk image is no longer available
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /target/*.jar demo.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","demo.jar"]