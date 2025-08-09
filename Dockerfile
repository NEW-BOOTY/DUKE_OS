# Copyright © 2025 Devin B. Royal.
# All Rights Reserved.

FROM eclipse-temurin:17-jdk-alpine

# Secure env setup
ENV APP_HOME=/opt/mdm
WORKDIR $APP_HOME

# Copy app + libs + configs
COPY device-manager.jar .
COPY lib/ lib/
COPY resources/ resources/
COPY webapp/ webapp/

# Secure config files
RUN chmod 600 resources/*

# Expose API port
EXPOSE 8080

# Default command
CMD ["java", "-cp", "device-manager.jar:lib/*", "device.manager.DeviceManagerLauncher"]
