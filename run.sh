#!/usr/bin/env bash
docker run -it --rm --name hotel-reservation -v $PWD/targetDeploy:/opt/fuse/redhat-jboss-fuse/deploy \
hotel-reservation