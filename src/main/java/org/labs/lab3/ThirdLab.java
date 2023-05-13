package org.labs.lab3;

import org.labs.lab3.component.CalculationService;
import org.labs.lab3.component.FileManager;

import java.util.LinkedHashMap;
import java.util.LinkedList;

public class ThirdLab {

    public static void main(String[] args) {
        CalculationService service = new CalculationService();
        FileManager fileManager = new FileManager();

        LinkedHashMap<Integer, LinkedList<Float>> input = fileManager.readDataFromFile();
        input = service.sortInput(input);
        fileManager.saveScatterDiagram(input);
        int numOfData = fileManager.readFirstNumberFromFile();
        float xOfGravity = service.findXOfGravity(input, numOfData);
        float yOfGravity = service.findYOfGravity(input, numOfData);

        fileManager.printToFile("Center of Gravity: (" + xOfGravity + ", " + yOfGravity + ")", true);

        float covariance = service.findCovariance(input, numOfData);
        fileManager.printToFile("Covariance: ", covariance, true);

        String equationOfRegressionLine = service.findEquationOfRegressionLine(input, numOfData);
        fileManager.printToFile("Equation Of Regression Line: " + equationOfRegressionLine, true);

        double correlationCoefficient = service.findCorrelationCoefficient(input, numOfData);
        fileManager.printToFile("Correlation Coefficient: " + correlationCoefficient, true);
    }
}
