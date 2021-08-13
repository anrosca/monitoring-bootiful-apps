FROM openjdk:16-alpine
COPY target/stock-profit-tracker-0.0.1-SNAPSHOT.jar /cpt/stock-profit-tracker.jar
RUN addgroup -S -g 2021 cpt && \
    adduser -S -g cpt -u 2021 cpt
RUN mkdir -p /cpt/
RUN mkdir -p /cpt/logs
COPY ./src/main/resources/docker/docker-configs.properties /cpt/application-docker.properties
RUN chown -R cpt:cpt /cpt
RUN find /cpt/ -type f -exec chmod 644 {} \; && chmod 775 /cpt/logs
USER cpt:cpt
ENV POSTGRES_JDBC_URL=jdbc:postgresql://cpt-operational-db:5432/stock-profit-tracker
ENV POSTGRES_PASSWORD=postgres
ENV POSTGRES_USER=postgres
ENV REDIS_HOST=redis
ENV REDIS_PORT=6379
ENV REDIS_PASSWORD=""
ENV REDIS_USERNAME=""
ENV IEX_API_KEY=""
EXPOSE 8080
EXPOSE 8081
EXPOSE 9010
WORKDIR /cpt
ENTRYPOINT [ "java",\
#"-Xms100m",\
#"-Xmx100m",\
#"-verbose:gc",\
#"-Xlog:gc:/cpt/logs/gc.log",\
#"-XX:+PrintGCDetails",\
"-Dcom.sun.management.jmxremote",\
"-Djava.rmi.server.hostname=127.0.0.1",\
"-Dcom.sun.management.jmxremote.port=9010",\
"-Dcom.sun.management.jmxremote.rmi.port=9010",\
"-Dcom.sun.management.jmxremote.local.only=false",\
"-Dcom.sun.management.jmxremote.authenticate=false",\
"-Dcom.sun.management.jmxremote.ssl=false",\
"-jar",\
"./stock-profit-tracker.jar"]
CMD ["--spring.profiles.active=docker"]
