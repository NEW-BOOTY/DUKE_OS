@echo off
REM ------------------------------------------------------------------------------
REM Copyright © 2024 Devin B. Royal.
REM All Rights Reserved.
REM ------------------------------------------------------------------------------
REM Windows Bootstrap Script for ChainSentinel
REM ------------------------------------------------------------------------------

echo [SentientNet::ChainSentinel] Starting blockchain node...

REM Load environment variables from sentinel.env
for /f "usebackq tokens=1,2 delims==" %%a in ("sentinel.env") do (
    if not "%%a"=="" set %%a=%%b
)

REM Check for JAR
if not exist chain-sentinel.jar (
    echo ERROR: chain-sentinel.jar not found!
    exit /b 1
)

REM Launch application
java %JAVA_OPTS% -jar chain-sentinel.jar %*
