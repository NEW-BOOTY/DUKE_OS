# Getting Started with NovaLink SDK

## Introduction
NovaLink SDK provides secure encryption, authentication, and network communication capabilities for modern applications. This guide will help you set up and integrate NovaLink SDK into your projects.

## Prerequisites
Before using NovaLink SDK, ensure you have the following installed:
- Java 11 or later
- Maven or Gradle (for dependency management)
- Network access to authentication and API endpoints

## Installation
### Using Maven
Add the following dependency to your `pom.xml`:
```xml
<dependency>
    <groupId>com.novalink</groupId>
    <artifactId>novalink-sdk</artifactId>
    <version>1.0.0</version>
</dependency>
```

### Using Gradle
Add this to your `build.gradle`:
```gradle
dependencies {
    implementation 'com.novalink:novalink-sdk:1.0.0'
}
```

## Configuration
Create a `config.properties` file in your resources directory:
```properties
token_endpoint=https://auth.example.com/token
client_id=your_client_id
client_secret=your_client_secret
refresh_token=your_refresh_token
```
Ensure that you load this configuration using `ConfigLoader` before making API calls.

## Basic Usage
### Initializing OAuth Authentication
```java
OAuthHandler auth = new OAuthHandler(
    ConfigLoader.getProperty("token_endpoint"),
    ConfigLoader.getProperty("client_id"),
    ConfigLoader.getProperty("client_secret"),
    ConfigLoader.getProperty("refresh_token")
);
String accessToken = auth.getAccessToken();
```

### Making a REST API Request
```java
RestClient client = new RestClient("https://api.example.com");
String response = client.get("/data", accessToken);
System.out.println(response);
```

### Making a GraphQL Request
```java
GraphQLClient gqlClient = new GraphQLClient("https://api.example.com/graphql");
String query = "{"query":"{ user { id name } }"}";
String gqlResponse = gqlClient.executeQuery(query, accessToken);
System.out.println(gqlResponse);
```

## Logging
Use the built-in logging utility for tracking events:
```java
Logger.info("Application started.");
Logger.warn("Potential configuration issue detected.");
Logger.error("An error occurred while processing the request.");
```

## Conclusion
NovaLink SDK is designed to enhance security and networking in your applications. Refer to `api_reference.md` for detailed API documentation and `faq.md` for common troubleshooting steps.
