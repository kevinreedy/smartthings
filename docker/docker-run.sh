#!/bin/sh
# docker run -itd -p 7678:7678 -v /home/pi/Projects/kevinreedy/smartthings/smartthings-nodeproxy/config.json:/stnp/config.json -e ENABLED_PLUGINS="rnet" --device /dev/ttyUSB0 --device /dev/vchiq smartthings-node-proxy:latest --name smartthings-node-proxy
docker run -it --rm -p 7680:7678 -v /home/pi/Projects/kevinreedy/smartthings/smartthings-nodeproxy/config.json:/stnp/config.json -e ENABLED_PLUGINS="rnet generic" --name smartthings-node-proxy-dev smartthings-node-proxy:dev
