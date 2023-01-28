FROM maven:3.8.5-openjdk-17
VOLUME /tmp
WORKDIR /app
COPY . .
RUN mvn clean install

CMD mvn spring-boot:run