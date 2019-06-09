FROM openjdk:8-jdk-alpine

LABEL maintainer="Andre Rocha <devel.andrerocha@gmail.com>"

RUN mkdir -p /var/lib/adobe && \
    mkdir -p /var/www

WORKDIR /var/lib/adobe

COPY devops/docker/docker-entrypoint.sh /var/lib/adobe/
COPY build/libs/adobe-test-webserver-all-1.0.jar /var/lib/adobe/
RUN chmod 777 /var/lib/adobe/docker-entrypoint.sh && \
    chmod 777 /var/lib/adobe/adobe-test-webserver-all-1.0.jar && \
    chmod 777 /var/www

#FIXME - Fix acess level of files inside the container

#start
VOLUME ["/var/www"]
EXPOSE 8080
ENTRYPOINT ["/var/lib/adobe/docker-entrypoint.sh"]