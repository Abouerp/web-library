#!/usr/bin/env bash

git fetch && \
git reset --hard origin/master

chmod +x ./mvnw && \
./mvnw clean compile jib:dockerBuild
docker run -d -p 8080:80 library/abouerp:latest
docker images | awk '$1 == "<none>" || $2 == "<none>" {print $3}' | xargs docker rmi


echo "deploy success"