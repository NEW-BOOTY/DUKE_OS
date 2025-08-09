#!/bin/bash
# ------------------------------------------------------------------------------
# Copyright © 2024 Devin B. Royal.
# All Rights Reserved.
# ------------------------------------------------------------------------------
# Shell Bootstrap Script for ChainSentinel
# ------------------------------------------------------------------------------

echo "[SentientNet::ChainSentinel] Starting blockchain node..."

# Load environment
if [ -f "sentinel.env" ]; then
    export $(grep -v '^#' sentinel.env | xargs)
fi

# Verify JAR existence
if [ ! -f "chain-sentinel.jar" ]; then
    echo "ERROR: chain-sentinel.jar not found!"
    exit 1
fi

# Launch the ChainSentinel blockchain node
exec java $JAVA_OPTS -jar chain-sentinel.jar "$@"
