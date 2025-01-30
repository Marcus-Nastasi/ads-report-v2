# Ads Report

## About the project

This is an application developed to facilitate the generation of Google Ads reports, designed on clean architecture.

The application allows you to retrieve data from your Google Ads accounts (manager and client), and return in csv or json format.

## Techs

### Architecture
- **Architecture**: Clean Architecture

### Back-end
- **Framework**: Spring Framework
- **Language**: Java 21
- **Tests**: JUnit and Mockito

### API Documentation
- **Tools**: Swagger API, Javadoc

## How to run

Follow the steps below to set up and run the project on your local machine.

## Prerequisites

- Git
- java 21 (JDK) e Maven
- Docker and Docker Compose

## Steps

**Make sure you have opened the ports 8080 (application) on your machine locally**

1. **Clone this repo**
   
2. **Configure the 'ads.properties' file on '/src/main/resources'.** 
  **You can see how to generate the credentials on the Google Ads Client Library for Java (https://developers.google.com/google-ads/api/docs/client-libs/java):**
   ```bash
    api.googleads.clientId=[your-client-id]
    api.googleads.clientSecret=[your-client-secret]
    api.googleads.refreshToken=[your-refersh-token]
    api.googleads.developerToken=[your-developer-token]
    loginCustomerId=[the-id-of-your-MCC]

3. **Configure the 'credentials.json' file on '/src/main/resources'.**
  **This file is your google cloud project's service account json file,**
  **for your google sheets connection. You can get orientation on the Google Sheets Client Library (https://developers.google.com/sheets/api/quickstart/java):**

4. **Run the app on your IDE, or run the docker-compose.yml file on "docker" directory:**
    ```bash
    [sudo] docker-compose up --build -d
