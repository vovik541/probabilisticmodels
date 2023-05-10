package org.labs.lab3.component;

import java.util.*;
import java.util.stream.Collectors;

public class CalculationService {

    public LinkedHashMap<Integer, LinkedList<Float>> sortInput(LinkedHashMap<Integer, LinkedList<Float>> input) {
        return reverseMap(input.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (x, y) -> y, LinkedHashMap::new)));
    }

    private float xOfGravity = 0;

    public float findXOfGravity(LinkedHashMap<Integer, LinkedList<Float>> input, int numOfData) {
        if (xOfGravity == 0) {
            float sum = 0;


            for (Map.Entry<Integer, LinkedList<Float>> entry : input.entrySet()) {
                for (Float num : entry.getValue()) {
                    sum += num;
                }
            }
            xOfGravity = sum / numOfData;
        }

        return xOfGravity;
    }

    private float yOfGravity = 0;

    public float findYOfGravity(LinkedHashMap<Integer, LinkedList<Float>> input, int numOfData) {
        if (yOfGravity == 0) {
            float sum = 0;

            for (Integer num : input.keySet()) {
                sum += num;
            }
            yOfGravity = sum / numOfData;
        }

        return yOfGravity;
    }
    private float covariance = 0;
    public float findCovariance(LinkedHashMap<Integer, LinkedList<Float>> input, int numOfData) {

        if (covariance == 0){
            float middleX = findXOfGravity(input, numOfData);
            float middleY = findYOfGravity(input, numOfData);

            for (Map.Entry<Integer, LinkedList<Float>> entry : input.entrySet()) {
                for (Float num : entry.getValue()) {
                    covariance += (num - middleX) * (entry.getKey() - middleY);
                }
            }
            covariance = covariance / numOfData;
        }

        return covariance;
    }

    public String findEquationOfRegressionLine(LinkedHashMap<Integer, LinkedList<Float>> input, int numOfData){
        float midX = findXOfGravity(input, numOfData);
        float midY = findYOfGravity(input, numOfData);
        float covXY = findCovariance(input, numOfData);
        float varX = findVarX(input, midX, numOfData);

        float b1 = covXY / varX;

        float c = b1 * -midX + midY;
        return String.format("y = %5.2f*x %s %2.2f", b1, (c > 0? "+" : "-" ) , c);

    }
    public double findCorrelationCoefficient(LinkedHashMap<Integer, LinkedList<Float>> input, int numOfData){
        float midX = findXOfGravity(input, numOfData);
        float midY = findYOfGravity(input, numOfData);
        float varX = findVarX(input, midX, numOfData);
        float varY = findVarY(input, midY, numOfData);
        double sX = Math.sqrt(varX);
        double sY = Math.sqrt(varY);

        float upperForm = 0;

        for (Map.Entry<Integer, LinkedList<Float>> entry : input.entrySet()) {
            for (Float num : entry.getValue()) {
                upperForm += ((num - midX)/sX)  * ((entry.getKey() - midY) / sY);
            }
        }

        return upperForm / (numOfData - 1);
    }

    private float varX = 0;
    public float findVarX(LinkedHashMap<Integer, LinkedList<Float>> input, float midX, int numOfData){
        if (varX == 0){
            float midXAbc = midX * midX;
            for (Map.Entry<Integer, LinkedList<Float>> entry : input.entrySet()) {
                for (Float num : entry.getValue()) {
                    varX += num * num;
                }
            }
            varX = (varX/ numOfData) - midXAbc;
        }
        return varX;
    }
    private float varY = 0;
    public float findVarY(LinkedHashMap<Integer, LinkedList<Float>> input, float midY, int numOfData){
        if (varY == 0){
            float midYAbc = midY * midY;
            for (Map.Entry<Integer, LinkedList<Float>> entry : input.entrySet()) {
                for (Float num : entry.getValue()) {
                    varY += entry.getKey() * entry.getKey();
                }
            }
            varY = (varY / numOfData) - midYAbc;
        }
        return varY;
    }
    public static <T, Q> LinkedHashMap<T, Q> reverseMap(LinkedHashMap<T, Q> toReverse) {
        LinkedHashMap<T, Q> reversedMap = new LinkedHashMap<>();
        List<T> reverseOrderedKeys = new ArrayList<>(toReverse.keySet());
        Collections.reverse(reverseOrderedKeys);
        reverseOrderedKeys.forEach((key) -> reversedMap.put(key, toReverse.get(key)));
        return reversedMap;
    }
}
