FROM ubuntu:latest AS google-cloud-sdk

RUN apt update && apt install -y apt-transport-https ca-certificates gnupg curl
RUN curl https://packages.cloud.google.com/apt/doc/apt-key.gpg | gpg --dearmor -o /usr/share/keyrings/cloud.google.gpg
RUN echo "deb [signed-by=/usr/share/keyrings/cloud.google.gpg] https://packages.cloud.google.com/apt cloud-sdk main" | tee -a /etc/apt/sources.list.d/google-cloud-sdk.list
RUN apt update && apt install -y google-cloud-cli

FROM openjdk:17-jdk AS build

WORKDIR /app

COPY . .

COPY --from=google-cloud-sdk /usr/share/keyrings/cloud.google.gpg /usr/share/keyrings/cloud.google.gpg
COPY gcp_credentials.json /app/gcp_credentials.json
ENV GOOGLE_APPLICATION_CREDENTIALS="/app/gcp_credentials.json"

RUN microdnf install findutils

RUN chmod +x ./gradlew
RUN ./gradlew build

FROM openjdk:17-jdk

WORKDIR /app

COPY --from=google-cloud-sdk /usr/share/keyrings/cloud.google.gpg /usr/share/keyrings/cloud.google.gpg
COPY gcp_credentials.json /app/gcp_credentials.json
ENV GOOGLE_APPLICATION_CREDENTIALS="/app/gcp_credentials.json"

COPY --from=build /app/build/libs/*.jar app.jar

COPY .env .env

ENTRYPOINT ["java", "-jar", "app.jar"]
