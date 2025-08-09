/*
 * ScalableAiApi.java
 *
 * A production-ready scalable image classification API using DJL and Javalin with WebSocket support.
 * Includes strong error handling, logging, model integration, and concurrent WebSocket management.
 *
 * Copyright © 2025 Devin B. Royal.
 * All Rights Reserved.
 */

package com.myndforge.ai;

import ai.djl.Model;
import ai.djl.ModelException;
import ai.djl.inference.Predictor;
import ai.djl.modality.Classifications;
import ai.djl.modality.cv.Image;
import ai.djl.modality.cv.ImageFactory;
import ai.djl.ndarray.NDList;
import ai.djl.translate.TranslateException;
import ai.djl.translate.Translator;
import ai.djl.translate.TranslatorContext;
import ai.djl.util.Utils;
import io.javalin.Javalin;
import io.javalin.http.UploadedFile;
import io.javalin.websocket.WsContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class ScalableAiApi {

    private static final Logger logger = LoggerFactory.getLogger(ScalableAiApi.class);
    private static final Path MODEL_DIR = Paths.get("models", "scalable_ai");
    private static final ConcurrentHashMap<String, WsContext> webSocketClients = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        try {
            Javalin app = Javalin.create(config -> {
                config.requestLogger((ctx, ms) -> logger.info("{} {} took {}ms", ctx.method(), ctx.path(), ms));
            }).start(7000);

            configureRoutes(app);
            configureWebSockets(app);

            logger.info("Scalable AI API started on port 7000.");
        } catch (Exception e) {
            logger.error("Failed to initialize Scalable AI API", e);
            System.exit(1);
        }
    }

    private static void configureRoutes(Javalin app) {
        app.post("/predict", ctx -> {
            UploadedFile file = ctx.uploadedFile("image");

            if (file == null) {
                logger.warn("No image file received in request.");
                ctx.status(400).result("Missing image file.");
                return;
            }

            try {
                Image img = ImageFactory.getInstance().fromInputStream(file.getContent());
                String prediction = processPrediction(img);
                ctx.result(prediction);
            } catch (IOException e) {
                logger.error("Error reading image file", e);
                ctx.status(500).result("Failed to process uploaded image.");
            }
        });
    }

    private static void configureWebSockets(Javalin app) {
        app.ws("/ws", ws -> {
            ws.onConnect(ctx -> {
                webSocketClients.put(ctx.getSessionId(), ctx);
                logger.info("WebSocket connected: {}", ctx.getSessionId());
            });

            ws.onClose(ctx -> {
                webSocketClients.remove(ctx.getSessionId());
                logger.info("WebSocket closed: {}", ctx.getSessionId());
            });

            ws.onError(ctx -> {
                logger.error("WebSocket error on session {}: {}", ctx.getSessionId(), ctx.error());
            });
        });
    }

    private static String processPrediction(Image img) {
        try (Model model = Model.newInstance(MODEL_DIR, "ai-model")) {
            Translator<Image, Classifications> translator = new Translator<>() {
                @Override
                public NDList processInput(TranslatorContext ctx, Image input) {
                    return new NDList(input.toNDArray(ctx.getNDManager()));
                }

                @Override
                public Classifications processOutput(TranslatorContext ctx, NDList list) {
                    List<String> synset = Utils.readLines(MODEL_DIR.resolve("synset.txt"));
                    return new Classifications(synset, list.singletonOrThrow());
                }

                @Override
                public Batchifier getBatchifier() {
                    return null;
                }
            };

            try (Predictor<Image, Classifications> predictor = model.newPredictor(translator)) {
                Classifications result = predictor.predict(img);
                return result.best().getClassName();
            }
        } catch (ModelException | IOException | TranslateException e) {
            logger.error("AI model prediction error", e);
            return "Prediction failed due to internal error.";
        }
    }
}

/*
 * Copyright © 2025 Devin B. Royal.
 * All Rights Reserved.
 */
