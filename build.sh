#!/bin/sh

USERNAME=jankudev
IMAGE=devilstorm-proxy

# build/package project
./mvnw package

# get JAR and pass to Dockerfile
JAR_FILE=`ls target/*.jar`

# build image
docker build --build-arg JAR_FILE="${JAR_FILE}" -t "${USERNAME}/${IMAGE}:latest" .
