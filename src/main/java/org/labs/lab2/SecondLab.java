package org.labs.lab2;

import org.labs.lab2.component.CalculationService;
import org.labs.lab2.component.FileManager;

import java.util.ArrayList;
import java.util.Collections;

public class SecondLab {
    public static void main(String[] args) {
        FileManager fileManager = new FileManager();
        CalculationService service = new CalculationService();

        ArrayList<Integer> input = fileManager.readDataFromFile();
        Collections.sort(input);

        double Q1 = service.countQ(input, 25);
        double Q3 = service.countQ(input, 75);
        double P90 = service.countQ(input, 90);

        fileManager.printToFile("Q1: ", Q1, false);
        fileManager.printToFile("Q3: ", Q3, true);
        fileManager.printToFile("P90: ", P90, true);

        double medium = service.getMedium(input);
        fileManager.printToFile("Medium: ", medium, true);

        double dispersion = service.getFixedDispersion(input);
        double meanSquareDeviation = service.getMeanSquareDeviation(dispersion);
        fileManager.printToFile("Mean Square Deviation: ", meanSquareDeviation, true);

        fileManager.saveTrunkLeaves(input);

        int median = service.getMedian(input);
        fileManager.saveBoxPlot(input, median, medium, input.get(input.size() - 1), input.get(0), Q1, Q3);

    }
}