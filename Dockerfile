FROM icr.io/appcafe/open-liberty:kernel-slim-java8-openj9-ubi

COPY --chown=1001:0 /src/main/resources/jdbc/mysql-connector-java-5.1.49.jar /config/jdbc/

COPY --chown=1001:0 /src/main/liberty/config /config

RUN features.sh

COPY --chown=1001:0 target/*.war /config/dropins/

RUN configure.sh
