FROM openjdk:11-jre-slim-stretch
RUN mkdir /app
COPY . /app
WORKDIR /app
CMD "java" "-jar" "app.jar"
