FROM gradle:7.6.2-jdk17 AS build
COPY . /home/gradle/project
WORKDIR /home/gradle/project
RUN gradle build -x test --no-daemon

FROM openjdk:17-jdk
COPY --from=build /home/gradle/project/build/libs/*.jar /app/app.jar
EXPOSE 8081
CMD ["java", "-jar", "/app/app.jar"]
