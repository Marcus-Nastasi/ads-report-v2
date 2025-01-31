# Ads Report

## About the project

This is an application developed to facilitate the generation of Google Ads reports, designed on clean architecture.

This second version allows any Google account user to log into the application with their account, and take advantage of 
the resources for their personal Google Ads and Google Sheets accounts.

The application can persist login data across a redis instance.

## Technologies

### Architecture
- **Architecture**: Clean Architecture

### Back-end
- **Language**: Java 21
- **Framework**: Spring Framework

### Persistency
- **Database**: Redis

### Authentication
- **Type**: OAuth2.0

### Unit tests
- **Tools**: JUnit and Mockito

### API Documentation
- **Tools**: Swagger API, Javadoc

## How to run
Follow the steps below to set up and run the project on your local machine.

## Prerequisites
- Git
- java 21 (JDK) e Maven
- Docker and Docker Compose

## Steps
**Make sure you have opened the ports 8080 (application) and 6379 (redis) on your machine locally**

1. **Clone this repository.**
   
2. **Configure the 'application.properties' file on '/src/main/resources'.** 
  **You can see how to generate the credentials on the Google Ads Client Library for Java (https://developers.google.com/google-ads/api/docs/client-libs/java):**
   ```bash
    # Google Cloud credentials
    api.googleads.clientId=..................
    api.googleads.clientSecret=..................
    api.googleads.developerToken=...............

    # Spring Security and OAuth2.0 config
    spring.security.oauth2.client.registration.google.client-id=..............
    spring.security.oauth2.client.registration.google.client-secret=.................
    spring.security.oauth2.client.registration.google.scope=openid,profile,email,https://www.googleapis.com/auth/adwords,https://www.googleapis.com/auth/spreadsheets
    spring.security.oauth2.client.registration.google.redirect-uri={baseUrl}/login/oauth2/code/google
    spring.security.oauth2.client.registration.google.user-name-attribute=name
    spring.security.oauth2.client.provider.google.authorization-uri=https://accounts.google.com/o/oauth2/auth?access_type=offline&prompt=consent
    spring.security.oauth2.client.provider.google.token-uri=https://oauth2.googleapis.com/token
    spring.security.oauth2.client.provider.google.user-info-uri=https://www.googleapis.com/oauth2/v3/userinfo
    spring.security.oauth2.client.provider.google.jwk-set-uri=https://www.googleapis.com/oauth2/v3/certs

    # Redis cache
    spring.cache.type = redis
    spring.redis.host = localhost
    spring.redis.port = 6379
    spring.data.redis.repositories.enabled = false
    spring.cache.redis.time-to-live=12h

3. **Run the app on your IDE, or run the docker-compose.yml file on "docker" directory:**
    ```bash
    [sudo] docker-compose up --build -d

4. **Then, you can access the login endpoint, and the swagger UI interface:**
    ```bash
    http://localhost:8080/login
    http://localhost:8080/swagger-ui/index.html

4. **Notes:**
- Even though dynamic login with Google eliminates the need for some static credentials, such as refresh token and service account 
  credentials, for the application to work you will still need your Google Cloud project credentials, such as client id and 
  client secret, in addition to Google Ads developer token.
