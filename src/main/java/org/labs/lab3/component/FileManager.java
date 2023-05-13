package org.labs.lab3.component;

import java.io.*;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Scanner;

import static org.labs.util.FilePathManager.getFilePath;


public class FileManager {

    private final String inputFile;
    private final String outputFile;

    public FileManager() {
        this.inputFile = getFilePath("input_15.txt", "lab3\\input");
        this.outputFile = getFilePath("output.txt", "lab3\\output");
    }

    public LinkedHashMap<Integer, LinkedList<Float>> readDataFromFile() {
        LinkedHashMap<Integer, LinkedList<Float>> input = new LinkedHashMap<>();
        String line;
        int key;
        float value;

        try (Scanner scanner = new Scanner(new File(inputFile))) {
            int numOfData = scanner.nextInt();
            scanner.nextLine();

            while (numOfData != 0) {
                line = scanner.nextLine();
                key = Integer.parseInt(line.split("\t")[1]);
                value = Float.parseFloat(line.split("\t")[0].replace(",", "."));
                if (input.containsKey(key)) {
                    input.get(key).add(value);
                } else {
                    float finalValue = value;
                    input.put(key, new LinkedList<>() {{
                        add(finalValue);
                    }});
                }
                --numOfData;
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return input;
    }

    public int readFirstNumberFromFile() {
        try (Scanner scanner = new Scanner(new File(inputFile))) {
            return scanner.nextInt();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void printToFile(String message, boolean append) {
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(outputFile, append)))) {

            out.println("\n" + message + " \n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void printToFile(String message, float num, boolean append) {
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(outputFile, append)))) {

            out.println("\n" + message + String.format("%4.2f", num) + " \n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveScatterDiagram(LinkedHashMap<Integer, LinkedList<Float>> sortedInput) {
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(outputFile, false)))) {
            out.println("\nScatter Diagram:\n");

            int biggest = sortedInput.entrySet().stream().findFirst().get().getKey();

            String line;
            LinkedList<Float> row;
            float current;
            float rightest = 0;

            while (biggest != 0) {
                line = String.format("%4d |", biggest);

                if (sortedInput.containsKey(biggest)) {
                    row = sortedInput.get(biggest);
                    Collections.sort(row);
                    current = 0;
                    for (Float next : row) {
                        if (next != current) {
                            line += " ".repeat((int) (Math.round((next - current) / 0.1) - 1)) + "*";
                            current = next;
                        }
                        if (rightest < next) {
                            rightest = next;
                        }
                    }


                }
                out.println(line);
                biggest--;

            }

            line = "     |" + "_".repeat((int) (rightest * 10) + 4);
            out.println(line);
            line = "     |" + "    |".repeat((int) (rightest * 10) / 5 + 1);
            out.println(line);

            line = "    ";

            for (float i = 0; i <= Math.ceil(rightest); i += 0.5) {
                line += String.format("%4.1f ", i);
            }
            out.println(line);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


}
