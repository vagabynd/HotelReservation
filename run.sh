#!/usr/bin/env bash

current=`pwd`
docker run --rm -d --name hotel-reservation --hostname hotel-reservation -p 8888:8080 -v ${current}/targetDeploy:/var/lib/jetty/webapps jetty