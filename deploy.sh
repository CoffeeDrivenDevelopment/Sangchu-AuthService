#!/bin/sh

##########
# ENV
##########

export AUTH_SERVICE_IMAGE_NAME="cdd/auth-service"
export AUTH_SERVICE_TAG_NAME="0.0.1"

##########
# Build Api Gateway Image
##########

echo "\n🗑 Start Delete Docker Files"

if docker image inspect $AUTH_SERVICE_IMAGE_NAME:$AUTH_SERVICE_TAG_NAME &> /dev/null; then
    docker image rm -f $AUTH_SERVICE_IMAGE_NAME:$AUTH_SERVICE_TAG_NAME
fi

echo "\n🔨 Start Build Docker Image"

docker build \
-t $AUTH_SERVICE_IMAGE_NAME:$AUTH_SERVICE_TAG_NAME .

##########
# Api Gateway Container Start
##########

if [ "$(docker ps -aq -f name=$AUTH_SERVICE_NAME)" ]; then
    echo "🚫 Stop Docker Container : "
    docker rm -f $AUTH_SERVICE_NAME
else
    echo "🚫 There is no running container named $AUTH_SERVICE_NAME"
fi

echo "🚀 Docker $AUTH_SERVICE_NAME Container Start! : "
docker run -d \
--name $AUTH_SERVICE_NAME \
-p $AUTH_SERVICE_PORT:$AUTH_SERVICE_PORT \
-e PROFILE=$AUTH_SERVICE_PROFILE \
$AUTH_SERVICE_IMAGE_NAME:$AUTH_SERVICE_TAG_NAME