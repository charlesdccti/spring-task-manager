#!/usr/bin/env bash

HTTP_STATUS="$( curl -i -X POST \
  http://localhost:8080/task-manager/api/login \
  -H 'content-type: application/x-www-form-urlencoded' \
  -d 'email=eric_cartman%40gmail.com&password=g5j%243p4xNxW37GQw' \
  | grep 200)";

if [ "${HTTP_STATUS}" != *$200 OK* ]; then
  printf "curl http://localhost:8080/task-manager/api/login \nTest failed"
  exit 1
else
  printf "curl http://localhost:8080/task-manager/api/login \nTest passed"
  exit 0
fi

