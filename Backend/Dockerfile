# naming it as "build", so i can acces this using multi-stage
FROM maven:3.9.16-eclipse-temurin-17 as build

#Directory base
WORKDIR /app

# Maven dependencys
COPY .mvn/ .mvn
COPY ./mvnw .
COPY pom.xml .

# first load all dependency for permance, because of docker sheets cache
RUN ./mvnw dependency:go-offline

COPY ./src/ ./src

# i still need to study how those maven terminal commands works
# but in general, that one just create a jar file of the project with all dependencys
RUN ./mvnw clean package -DskipTests -B

FROM eclipse-temurin:17-jre

WORKDIR /app
COPY --from=build ./app/target/*.jar app.jar

EXPOSE 8080:8080
ENTRYPOINT ["java", "-jar", "app.jar"]

