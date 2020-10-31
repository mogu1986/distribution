FROM harbor.top.mw/library/jdk:8u212-alpine
MAINTAINER ops@fengjing.com
ARG JAR_PATH
ARG JAR_NAME=app
ENV TZ Asia/Shanghai
ENV JAR_NAME=$JAR_NAME
ENV JAVA_OPTS="-Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom"
ADD $JAR_PATH /$JAR_NAME.jar
EXPOSE 8080 9991
ENTRYPOINT ["/sbin/tini", "--", "/bin/sh", "-c", "java $JAVA_OPTS -jar /$JAR_NAME.jar  $0 $@"]