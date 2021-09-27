#!/bin/bash
# http://wiki.obrienlabs.cloud/display/DEV/AWS+ECR+E2E+Architecture
# source from http://jira.obrienlabs.cloud/browse/ECS-1
# https://github.com/obrienlabs/refarch
# Michael O'Brien

BUILD_ID=10001
BUILD_DIR=builds
mkdir ../../$BUILD_DIR
TARGET_DIR=../../$BUILD_DIR/$BUILD_ID
mkdir $TARGET_DIR
CONTAINER_IMAGE=reference-nbi
cd ../../
mvn clean install -U -DskipTests=true
cd src/docker
cp ../../target/*.jar $TARGET_DIR
cp DockerFile $TARGET_DIR
cp startService.sh $TARGET_DIR
cd $TARGET_DIR
docker build --no-cache --build-arg build-id=$BUILD_ID -t obrienlabs/$CONTAINER_IMAGE -f DockerFile .
#docker tag $CONTAINER_IMAGE:latest $CONTAINER_IMAGE:latest
docker tag obrienlabs/$CONTAINER_IMAGE obrienlabs/$CONTAINER_IMAGE:0.0.1
# dockerhub
#docker push obrienlabs/$CONTAINER_IMAGE:0.0.1
# locally
docker stop $CONTAINER_IMAGE
docker rm $CONTAINER_IMAGE
echo "starting: $CONTAINER_IMAGE"
# echo -n 'password2' | base64
#docker run --name $CONTAINER_IMAGE \
#    -d -p 8888:8080 \
#    -e spring.user.password=password2 \
#    -e os.environment.configuration.dir=/ \
#    -e os.environment.ecosystem=sbx \
#    obrienlabs/$CONTAINER_IMAGE:0.0.1

cd ../../src/docker

#helm3 delete reference-nbi
#sudo helm3 package reference-nbi
#helm3 install --set name=reference-nbi reference-nbi ./reference-nbi