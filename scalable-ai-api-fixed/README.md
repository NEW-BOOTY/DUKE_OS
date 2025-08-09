# ScalableAiApi

## Overview

`ScalableAiApi` is a scalable, production-ready image classification server built using:

- 🧠 **DJL (Deep Java Library)** for inference
- ⚡ **Javalin** for fast HTTP + WebSocket support
- 🔐 Full error handling, logging, and modularity

---

## 🛠 Project Structure

scalable-ai-api/
├── build.gradle
├── README.md
├── models/
│ └── scalable_ai/
│ ├── ai-model/
│ └── synset.txt
└── src/
└── main/
└── java/
└── com/
└── myndforge/
└── ai/
└── ScalableAiApi.java


---

## 🚀 Running the App

```bash
# Build and run
./gradlew build
java -cp build/libs/scalable-ai-api.jar com.myndforge.ai.ScalableAiApi
Server runs on:
📡 http://localhost:7000

📤 Image Prediction (REST)

Endpoint:
POST /predict

Form Data:
image → Upload an image file (JPG/PNG)
Response:
Best predicted class label
🔄 Real-Time (WebSocket)

Endpoint:
ws://localhost:7000/ws

Handles:

Connection logging
Session management
Ready for broadcast extensions
✅ Requirements

Java 17+
Internet access (first run downloads model backend if needed)
🧠 Model Info

Ensure your model and synset.txt file are in:

models/scalable_ai/
├── ai-model/         # Your saved model dir (e.g., TensorFlow SavedModel)
├── synset.txt        # Class labels in correct order
🔐 Security & Logging

Full SLF4J logging
Graceful error handling
Validation on file uploads
WebSocket client management with ConcurrentHashMap
🧾 License

Copyright © 2025 Devin B. Royal  
All Rights Reserved.

---


