FROM openjdk:17-alpine

EXPOSE 8082

ENV TZ=Asia/Seoul

ARG DB_URL
ENV DB_URL ${DB_URL}
ARG DB_USERNAME
ENV DB_USERNAME ${DB_USERNAME}
ARG DB_PASSWORD
ENV DB_PASSWORD ${DB_PASSWORD}

ARG JAR_FILE=/build/libs/market-customer-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} /app.jar

ENTRYPOINT ["java", "-jar", "-Duser.timezone=Asia/Seoul", "/app.jar"]
