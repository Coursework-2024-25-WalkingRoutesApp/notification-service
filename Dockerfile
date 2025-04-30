FROM amazoncorretto:23-alpine-jdk

WORKDIR /notification-service
ARG VERSION
ADD build/libs/notification-service-${VERSION}.jar notification-service.jar

CMD ["java", "-jar", "notification-service.jar"]
