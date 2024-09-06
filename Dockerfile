FROM quay.io/wildfly/wildfly:latest-jdk17

# esse usuário foi criado para hot deploy
RUN /opt/jboss/wildfly/bin/add-user.sh admin admin --silent

EXPOSE 8080 9990

CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]