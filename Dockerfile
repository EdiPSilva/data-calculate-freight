FROM openjdk:17
MAINTAINER Edi Silva
COPY build/libs/*.jar data-calculate-freight.jar
EXPOSE 8080
CMD ["java", "-jar", "data-calculate-freight.jar"]