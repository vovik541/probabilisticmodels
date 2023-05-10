package org.labs.lab2.component;

import java.io.*;
import java.util.*;

import static org.labs.util.FilePathManager.getFilePath;


public class FileManager {

    private final String inputFile;
    private final String outputFile;

    public FileManager() {
        this.inputFile = getFilePath("input_10.txt", "lab2\\input");
        this.outputFile = getFilePath("output.txt", "lab2\\output");
    }

    public ArrayList<Integer> readDataFromFile() {
        ArrayList<Integer> input = new ArrayList<>();
        int number;

        try (Scanner scanner = new Scanner(new File(inputFile))) {
            int numOfData = scanner.nextInt();

            while (numOfData != 0) {
                number = scanner.nextInt();

                input.add(number);
                --numOfData;
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return input;
    }

    public void printToFile(String message, double num, boolean append) {
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(outputFile, append)))) {

            out.println(message + num + " \n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void printToFile(String message, boolean append) {
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(outputFile, append)))) {

            out.println(message + " \n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveBoxPlot(ArrayList<Integer> data, int median, double mean, int biggest, int smallest, double Q1, double Q3) {
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(outputFile, true)))) {
            out.println("\nBox Plot before scaling:\n");

            int lineWidth = 30;

            out.println(String.format("%4d |", biggest) + " ".repeat(12) + "-".repeat(lineWidth));


            while (Math.ceil(Q3) + 1 != biggest) {
                biggest--;
                out.println(String.format("%4d |", biggest) + " ".repeat(12 + lineWidth / 2) + "|");
            }
            biggest--;
            out.println(String.format("%4d |", biggest) + " ".repeat(12) + "-".repeat(lineWidth));

            int spotted = 0;
            if (Math.ceil(mean) == median) {
                while (spotted != 1) {
                    biggest--;
                    if (median == biggest) {
                        spotted++;
                        out.println(String.format("%4d |", biggest) + " ".repeat(12) + "|" + String.format("%8s %3d %8s %3.1f", "Median", median, "Mean", mean) + "  |");
                        continue;
                    }

                    out.println(String.format("%4d |", biggest) + " ".repeat(12) + "|" + " ".repeat(lineWidth - 2) + "|");
                }
            } else {
                while (spotted != 2) {
                    biggest--;
                    if (Math.ceil(mean) == biggest) {
                        spotted++;
                        out.println(String.format("%4d |", biggest) + " ".repeat(12) + "|       " + String.format("%7s %3.1f", "Mean", mean) + "         |");
                        continue;
                    }
                    if (median == biggest) {
                        spotted++;
                        out.println(String.format("%4d |", biggest) + " ".repeat(12) + "|       " + String.format("%9s %3d", "Median", median) + "        |");
                        continue;
                    }

                    out.println(String.format("%4d |", biggest) + " ".repeat(12) + "|" + " ".repeat(lineWidth - 2) + "|");
                }
            }


            while (Math.ceil(Q1) + 1 != biggest) {
                biggest--;
                out.println(String.format("%4d |", biggest) + " ".repeat(12) + "|" + " ".repeat(lineWidth - 2) + "|");
            }

            biggest--;
            out.println(String.format("%4d |", biggest) + " ".repeat(12) + "-".repeat(lineWidth));

            while (smallest + 1 != biggest) {
                biggest--;
                out.println(String.format("%4d |", biggest) + " ".repeat(12 + lineWidth / 2) + "|");
            }
            biggest--;
            out.println(String.format("%4d |", biggest) + " ".repeat(12) + "-".repeat(lineWidth));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    public void saveTrunkLeavesBeforeScaling(ArrayList<Integer> data) {
        LinkedHashMap<Integer, List<Integer>> leaves = new LinkedHashMap<>();

        int devoted = 0;

        for (Integer num : data) {
            devoted = num / 10;
            if (leaves.containsKey(devoted)) {
                leaves.get(devoted).add(num % 10);
            } else {
                leaves.put(devoted, new LinkedList<>() {{
                    add(num % 10);
                }});
            }
        }

        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(outputFile, true)))) {
            out.println("\nTrunk-leaves diagram before scale:\n");

            int length = findBiggest(leaves);

            out.println("trunk | leaves");
            out.println("_".repeat(7) + "_".repeat(4 * length));
            for (Map.Entry<Integer, List<Integer>> entry : leaves.entrySet()) {
                out.print(String.format(" %4d |", entry.getKey()));
                for (Integer num : entry.getValue()) {
                    out.print(String.format("%4d", num));
                }
                out.println("");
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void saveTrunkLeavesAfterScaling(ArrayList<Float> data) {
        LinkedHashMap<Integer, List<Float>> leaves = new LinkedHashMap<>();

        int devoted = 0;

        for (Float num : data) {
            devoted = Math.round(num) / 10;
            if (leaves.containsKey(devoted)) {
                leaves.get(devoted).add(num % 10);
            } else {
                leaves.put(devoted, new LinkedList<>() {{
                    add(num % 10);
                }});
            }
        }

        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(outputFile, true)))) {
            out.println("\nTrunk-leaves diagram after scale:\n");

            float length = findBiggestFloat(leaves);

            out.println("trunk | leaves");
            out.println("_".repeat(7) + "_".repeat(4 * (int) length));
            for (Map.Entry<Integer, List<Float>> entry : leaves.entrySet()) {
                out.print(String.format(" %4d |", entry.getKey()));
                for (Float num : entry.getValue()) {
                    out.print(String.format("%5.1f", num));
                }
                out.println("");
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public int findBiggest(LinkedHashMap<Integer, List<Integer>> leaves) {
        int biggest = 0;

        for (Map.Entry<Integer, List<Integer>> entry : leaves.entrySet()) {
            if (entry.getValue().size() > biggest) {
                biggest = entry.getValue().size();
            }
        }

        return biggest;
    }

    public float findBiggestFloat(LinkedHashMap<Integer, List<Float>> leaves) {
        float biggest = 0;

        for (Map.Entry<Integer, List<Float>> entry : leaves.entrySet()) {
            if (entry.getValue().size() > biggest) {
                biggest = entry.getValue().size();
            }
        }

        return biggest;
    }
}
