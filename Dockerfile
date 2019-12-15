FROM harbor.jq.cn/library/jdk:8u212-alpine
MAINTAINER session.blue@gmail.com
ADD distribution-service/target/distribution-service.jar /distribution-service.jar
ENTRYPOINT ["/bin/sh", "-c", "java -jar $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom /distribution-service.jar $0 $@"]