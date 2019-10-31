FROM openjdk:8-alpine

ARG PUID
ARG PGID
ARG SOURCEAPP

RUN apk add --no-cache bash
RUN addgroup -g ${PGID} alpinedocker && \
        adduser -D -u ${PUID} -G alpinedocker alpinedocker

USER alpinedocker
RUN mkdir -p /home/alpinedocker
WORKDIR /home/alpinedocker


ADD ${SOURCEAPP} /home/alpinedocker/application.jar


ENTRYPOINT ["/bin/bash", "-c", "set -e && java $JAVA_OPTS  -jar /home/alpinedocker/application.jar"]
