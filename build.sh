#!/bin/bash
# build.sh - Compile CLI + Jetty system
# Copyright © 2025 Devin B. Royal. All Rights Reserved.

SRC_DIR="src"
OUT_DIR="out"
LIB_DIR="lib"

echo "[Build] Cleaning output directory..."
rm -rf "$OUT_DIR"
mkdir -p "$OUT_DIR"

echo "[Build] Compiling all modules..."
javac -cp "$LIB_DIR/*" -d "$OUT_DIR" $(find "$SRC_DIR" -name "*.java")

if [ $? -eq 0 ]; then
  echo "[Build] ✅ Compilation succeeded."
else
  echo "[Build] ❌ Compilation failed."
  exit 1
fi
