# ------------------------------------------------------------------------------
# Copyright © 2024 Devin B. Royal.
# All Rights Reserved.
# ------------------------------------------------------------------------------
# Dockerfile for the chain-sentinel module
# Base: OpenJDK LTS for production-grade JVM reliability
# ------------------------------------------------------------------------------

    FROM eclipse-temurin:17-jdk-alpine

    LABEL maintainer="Devin B. Royal"
    LABEL project="SentientNet - ChainSentinel"
    
    # Define working directory
    WORKDIR /opt/sentient/chain-sentinel
    
    # Copy Maven build artifact (jar must be built via mvn install first)
    COPY target/chain-sentinel-1.0.0.jar chain-sentinel.jar
    
    # Copy bootstrap scripts and environment
    COPY sentinel-start.sh .
    COPY sentinel.env .
    
    # Mark the entry script as executable
    RUN chmod +x sentinel-start.sh
    
    # Set environment variables
    ENV JAVA_OPTS="-Xms128m -Xmx512m"
    ENV SENTINEL_MODE=DOCKERIZED
    
    # Entrypoint
    ENTRYPOINT ["./sentinel-start.sh"]
    