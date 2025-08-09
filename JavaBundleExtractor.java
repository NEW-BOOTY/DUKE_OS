/*
 * Copyright © 2024 Devin B. Royal.
 * All Rights Reserved.
 */

import java.io.*;
import java.nio.file.*;
import java.util.List;

public class JavaBundleExtractor {

    private static final String DELIMITER = "---------";
    private static final String OUTPUT_DIR = "output_java_files";

    public static void main(String[] args) {
        String bundleFile = "my_programs.java_bundle"; // Replace with your file name

        try {
            // Read the entire bundle file
            List<String> lines = Files.readAllLines(Paths.get(bundleFile));
            StringBuilder programBuilder = new StringBuilder();
            int programCounter = 1;

            // Create the output directory
            Files.createDirectories(Paths.get(OUTPUT_DIR));

            for (String line : lines) {
                if (line.trim().equals(DELIMITER)) {
                    // Write the current program to a .java file
                    writeProgramToFile(programBuilder.toString(), programCounter++);
                    programBuilder.setLength(0); // Reset for the next program
                } else {
                    programBuilder.append(line).append(System.lineSeparator());
                }
            }

            // Write the last program (if exists)
            if (programBuilder.length() > 0) {
                writeProgramToFile(programBuilder.toString(), programCounter);
            }

            System.out.println("Extraction complete. Files saved to: " + OUTPUT_DIR);
        } catch (IOException e) {
            System.err.println("Error processing bundle file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void writeProgramToFile(String program, int programNumber) throws IOException {
        String fileName = OUTPUT_DIR + "/Program" + programNumber + ".java";
        Files.write(Paths.get(fileName), program.getBytes());
        System.out.println("Program " + programNumber + " saved as " + fileName);
    }
}
