#!/bin/sh
# docker build -t smartthings-node-proxy:latest --build-arg architecture=armv7 .
docker build -t smartthings-node-proxy:dev --build-arg architecture=armv7 .
