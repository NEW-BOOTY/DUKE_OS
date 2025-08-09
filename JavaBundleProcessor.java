/*
 * Copyright © 2024 Devin B. Royal.
 * All Rights Reserved.
 */

import java.io.*;
import java.nio.file.*;
import java.util.List;

public class JavaBundleProcessor {

    private static final String DELIMITER = "---------";
    private static final String OUTPUT_DIR = "output_java_files";

    public static void main(String[] args) {
        String bundleFile = "SourceCode.java_bundle"; // Replace with your file name

        try {
            // Step 1: Extract Java files
            extractJavaFiles(bundleFile);

            // Step 2: Compile extracted files
            compileJavaFiles();

            // Step 3: Run compiled programs
            runJavaPrograms();

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void extractJavaFiles(String bundleFile) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(bundleFile));
        StringBuilder programBuilder = new StringBuilder();
        int programCounter = 1;

        Files.createDirectories(Paths.get(OUTPUT_DIR));

        for (String line : lines) {
            if (line.trim().equals(DELIMITER)) {
                writeProgramToFile(programBuilder.toString(), programCounter++);
                programBuilder.setLength(0);
            } else {
                programBuilder.append(line).append(System.lineSeparator());
            }
        }

        if (programBuilder.length() > 0) {
            writeProgramToFile(programBuilder.toString(), programCounter);
        }

        System.out.println("Extraction complete. Files saved to: " + OUTPUT_DIR);
    }

    private static void writeProgramToFile(String program, int programNumber) throws IOException {
        String fileName = OUTPUT_DIR + "/Program" + programNumber + ".java";
        Files.write(Paths.get(fileName), program.getBytes());
        System.out.println("Program " + programNumber + " saved as " + fileName);
    }

    private static void compileJavaFiles() throws IOException, InterruptedException {
        ProcessBuilder builder = new ProcessBuilder("javac", "*.java");
        builder.directory(new File(OUTPUT_DIR));
        builder.inheritIO();
        Process process = builder.start();
        int exitCode = process.waitFor();
        if (exitCode == 0) {
            System.out.println("Compilation successful.");
        } else {
            System.err.println("Compilation failed.");
        }
    }

    private static void runJavaPrograms() throws IOException, InterruptedException {
        File dir = new File(OUTPUT_DIR);
        for (File file : dir.listFiles((d, name) -> name.endsWith(".java"))) {
            String className = file.getName().replace(".java", "");
            ProcessBuilder builder = new ProcessBuilder("java", className);
            builder.directory(dir);
            builder.inheritIO();
            Process process = builder.start();
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("Executed: " + className);
            } else {
                System.err.println("Execution failed for: " + className);
            }
        }
    }
}
