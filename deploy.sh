#!/usr/bin/env bash

export env=dev
export name=distribution
export jar_path=distribution-service/target/distribution-service.jar
export host=distribution.test.mw
export registry=harbor.top.mw/library
export version=20
export image=$registry/$name:$version


mvn clean package -Dmaven.test.skip=true -U

docker build -t $image --no-cache --build-arg JAR_PATH=$jar_path --build-arg JAR_NAME=$name .

docker push $image

docker rmi $image

helm un $name -n $env

helm install $name -n $env --version 0.1 meiwu/meiwu \
--set nameOverride=$name \
--set image.repository=$registry/$name \
--set image.tag=$version \
--set ingress.enabled=true \
--set ingress.hosts[0]=$host \
--set jasypt.enabled=false \
--set msepilot.enabled=true \
--set spring.profiles.active=$env
