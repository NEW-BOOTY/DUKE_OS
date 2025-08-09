#!/bin/bash
# run.sh - Start unified MDM + Android Enterprise app
# Copyright © 2025 Devin B. Royal. All Rights Reserved.

OUT_DIR="out"
LIB_DIR="lib"

MAIN_CLASS="device.manager.DeviceManagerLauncher"

echo "[Run] 🚀 Starting CLI + Jetty system..."
java -cp "$OUT_DIR:$LIB_DIR/*" "$MAIN_CLASS"
