# Use openjdk 17-jdk-alpine as the base image
FROM openjdk:17-jdk-alpine

# Copy the application files from the host machine to the Docker directory
COPY build/libs/ms-tenant-1.0.jar /ms-tenant-1.0.jar

# Specify the default instruction for your container
ENTRYPOINT ["java","-jar","/ms-tenant-1.0.jar"]

## To build run
# docker build -t webflux:1.0 .

# -t specify the image name and tag (no tag - default to "latest"
# then specify the path where Dockerfile is located - if current specify "." for current