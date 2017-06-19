## Simple Task Manager

### Introduction
This is a simple REST API backend for Task Manager with Spring MVC/JPA/Security, MySQL docker containers

##
#### Clone project:
```
git clone https://github.com/tsarenkotxt/spring-task-manager.git
cd spring-task-manager
```

#### Build docker image
```
mvn package docker:build
```

#### Run application
```
docker-compose up 
```
##
#### Tomcat web application manager
```
http://localhost:8080/manager
username="tomcat" password="tomcat"
```

#### Task Manager
```
http://localhost:8080/task-manager/api/**
```






