# 基础镜像
FROM openjdk:8-jdk-alpine

# 设置时区
ENV TZ=Asia/Shanghai

# 指定工作目录
WORKDIR /app

# 将 jar 包添加到工作目录，比如 target/yuoj-backend-user-service-0.0.1-SNAPSHOT.jar
ADD target/HeFengLibrary-0.0.1-SNAPSHOT.jar .

# 暴露端口
EXPOSE 8201

# 启动命令
ENTRYPOINT ["java","-jar","/app/HeFengLibrary-0.0.1-SNAPSHOT.jar","--spring.profiles.active=prod"]