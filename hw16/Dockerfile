FROM library/maven
WORKDIR /app

COPY . .
RUN mvn package

EXPOSE 8080

CMD java -jar /app/target/hw16-0.0.1-SNAPSHOT.jar