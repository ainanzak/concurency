#FROM tomcat:jre8-alpine
#RUN apk add --no-cache tzdata
#ENV TZ Asia/Bangkok
#RUN mkdir -p /usr/src/app/
#RUN mkdir -p /usr/src/app/apps-log
#COPY ./target/Concurency.jar /usr/src/app/app.jar
#WORKDIR /usr/src/app
#ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","app.jar"]

#FROM maven:3.5.4-jdk-8-alpine

#docker-compose up --build --force-recreate -d
#docker-compose down
#docker rmi concurency/api:latest
# docker-compose up --force-recreate -d