FROM tomcat:8.0-jre8
COPY tomcat-users.xml /usr/local/tomcat/conf
COPY task-manager-backend-0.1.0.war /usr/local/tomcat/webapps/task-manager.war
CMD ["catalina.sh", "run"]

