package org.labs.lab1.component;

import java.io.*;
import java.util.*;

import static org.labs.util.FilePathManager.getFilePath;

public class FileManager {

    private final String inputFile;
    private final String outputFile;

    public FileManager() {
        this.inputFile = getFilePath("input_100.txt", "input");
        this.outputFile = getFilePath("output.txt", "output");
    }

    public HashMap<Integer, Integer> readDataFromFile() {
        HashMap<Integer, Integer> input = new HashMap<>();
        int number;

        try (Scanner scanner = new Scanner(new File(inputFile))) {
            int numOfData = scanner.nextInt();

            while (numOfData != 0) {
                number = scanner.nextInt();

                if (input.containsKey(number)) {
                    input.put(number, input.get(number) + 1);
                } else {
                    input.put(number, 1);
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

    public void saveFrequencyTable(LinkedHashMap<Integer, Integer> frequencyTable) {
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(outputFile, false)))) {

            out.println("Frequency Table: \n");
            out.println(String.format("|%10s |%10s |", "Xi", "Ni"));
            out.println("_".repeat(25));

            for (Map.Entry<Integer, Integer> entry : frequencyTable.entrySet()) {
                out.println(String.format("|%10d |%10d |", entry.getKey(), entry.getValue()));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveMostViewed(int mostViewed) {
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(outputFile, true)))) {
            out.println("\nMost viewed Xi: " + mostViewed);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveMode(int mode) {
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(outputFile, true)))) {
            out.println("\nMode is: " + mode);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveMedian(int median) {
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(outputFile, true)))) {
            out.println("\nMedian is: " + median);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveDispersion(double dispersion) {
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(outputFile, true)))) {
            out.println(String.format("\nDispersion is: %1.2f", dispersion));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveMeanSquareDeviation(double meanSquareDeviation) {
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(outputFile, true)))) {
            out.println(String.format("\nMean Square Deviation is: %1.2f", meanSquareDeviation));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveHistogram(LinkedHashMap<Integer, Integer> data, double step) {

        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(outputFile, true)))) {
            out.println("\nHistogram is:\n");

            int stepNumber = findBiggestStepNumber(data);

            int leftIndentation = 12;
            String emptyLine = " ".repeat(leftIndentation) + "|";

            while (stepNumber != 0) {
                if (data.containsKey(stepNumber)) {
                    out.println(String.format("%" + (leftIndentation - 2) + ".2f -|", step * stepNumber));
                    out.println(emptyLine + "*".repeat(data.get(stepNumber) * 2));
                    out.println(emptyLine + "*".repeat(data.get(stepNumber) * 2));
                } else {
                    out.println(String.format("%" + (leftIndentation - 2) + ".2f -|", step * stepNumber));
                    out.println(emptyLine);
                    out.println(emptyLine);
                }

                stepNumber--;
            }

            String bottNumberLine = "";
            int i = -5;
            do {
                i += 5;
                bottNumberLine += String.format("%9d|", i);
            } while (i < findBiggestSequenceNumber(data));

            String bottLine = "_".repeat(i * 2);

            out.println(" ".repeat(leftIndentation) + "|" + bottLine);
            out.println(" ".repeat(leftIndentation - 9) + bottNumberLine);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private int findBiggestStepNumber(LinkedHashMap<Integer, Integer> data) {
        List<Map.Entry<Integer, Integer>> entries =
                new ArrayList<>(data.entrySet());
        return entries.get(entries.size() - 1).getKey();
    }

    private int findBiggestSequenceNumber(LinkedHashMap<Integer, Integer> data) {
        List<Map.Entry<Integer, Integer>> entries =
                new ArrayList<>(data.entrySet());
        Collections.sort(entries, Map.Entry.comparingByValue());
        return entries.get(entries.size() - 1).getValue();
    }

}
