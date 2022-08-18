FROM openjdk:11
RUN ./gradlew clean --debug build -Pprofile=prod
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 7777/tcp
ARG SPRING_PROFILES_ACTIVE
ENV SPRING_PROFILES_ACTIVE=$SPRING_PROFILES_ACTIVE
ENTRYPOINT ["java","-jar","-Dspring.config.location=/home/ec2-user/app/application-prod.properties","/app.jar"]
