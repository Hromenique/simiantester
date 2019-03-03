#Primeiro gere um fat jar com o Maven:
#mvn package
#
#Depois faça o build da imagem com o Docker:
# docker build -t hromenique/simiantester:1.0.0 -t hromenique/simiantester:latest .
#
#Para executar um novo container:
# docker run -p 8080:8080 --name simiantester hromenique/simiantester:<version>

FROM openjdk:8
ENV version=1.0.0
ENV PROFILE=default
LABEL version=${version}
COPY target/simiantester*.jar /var/www/app.jar
EXPOSE 8080
WORKDIR /var/www
ENTRYPOINT java -Dspring.profiles.active=${PROFILE} -jar app.jar
