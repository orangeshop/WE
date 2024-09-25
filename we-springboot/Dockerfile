FROM openjdk:17

# tzdata 패키지 설치
RUN apk add --no-cache tzdata

CMD ["./gradlew","clean", "build"]

VOLUME /tmp

ARG JAR_FILE=we-springboot/build/libs/*.jar

COPY ${JAR_FILE} app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app.jar"]
