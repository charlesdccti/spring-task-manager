[![Build Status](https://travis-ci.org/tsarenkotxt/spring-task-manager.svg?branch=master)](https://travis-ci.org/tsarenkotxt/spring-task-manager)
## Task Manager
### Introduction
This is a simple RESTful API backend for the Task Manager, 
in which users can perform basic CRUD operations on their tasks, 
users can also share their tasks and receive notifications by email.
With registration / authorization and a role-based interface (USER, ADMIN). 
REST interface is covered by JUnit tests using Spring MVC Test and Spring Security Test.
##
### Technology 
- Spring JavaConfig
  - MVC
  - Security
  - Data JPA
     - Profiles
- Hibernate
  - Validation
- Jackson
- MySQL 
- HSQLDB 
- Logback 
  - SLF4J 
- JUnit  
- Docker 
  - Docker Compose 
##
### Get started
```
git clone https://github.com/tsarenkotxt/spring-task-manager.git
cd spring-task-manager
mvn package docker:build
docker-compose up 
```
### Tomcat web application manager
```
http://localhost:8080/manager
username="tomcat" password="tomcat"
```
### Task Manager
```
http://localhost:8080/task-manager/api/**
```
##
### Users 
![Users sub-resources restful api](http://drive.google.com/uc?export=download&id=0B5RzgDks1Z9XWVRBc1liNW5hMVk)
##
### Users sub-resources 
![Authentication restful api](http://drive.google.com/uc?export=download&id=0B5RzgDks1Z9XNVFadHRUMmhwNlU)
##
### Tasks 
![Tasks restful api](http://drive.google.com/uc?export=download&id=0B5RzgDks1Z9XS01RWXFTQURuWjA)
##
### Tasks sub-resources 
![Tasks sub-resources restful api](http://drive.google.com/uc?export=download&id=0B5RzgDks1Z9XbmJBSkZfSkhyRW8)
##
### Authentication 
![Authentication restful api](http://drive.google.com/uc?export=download&id=0B5RzgDks1Z9XeF9FT05Rb0REREk)
##
### Exceptions response 
| Exception                              | ResponseCode | ResponseBody                                          |
|----------------------------------------|:------------:|-------------------------------------------------------|
| BindException                          | 400          | { "url" : "...", "cause" : "...", "details" : [...] } |
| MethodArgumentNotValidException        | 400          | { "url" : "...", "cause" : "...", "details" : [...] } |
| ValidationException                    | 400          | { "url" : "...", "cause" : "...", "details" : [...] } |
| InternalAuthenticationServiceException | 401          | { "url" : "...", "cause" : "...", "details" : [...] } |
| AccessDeniedException                  | 403          | { "url" : "...", "cause" : "...", "details" : [...] } |
| NoHandlerFoundException                | 404          | { "url" : "...", "cause" : "...", "details" : [...] } |
| EntityAlreadyExistsException           | 409          | { "url" : "...", "cause" : "...", "details" : [...] } |
| HttpMediaTypeNotSupportedException     | 415          | { "url" : "...", "cause" : "...", "details" : [...] } |
| EntityNotFoundException                | 422          | { "url" : "...", "cause" : "...", "details" : [...] } |
| Exception                              | 500          | { "url" : "...", "cause" : "...", "details" : [...] } |



