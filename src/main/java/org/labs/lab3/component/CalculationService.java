package org.labs.lab3.component;

import java.io.File;
import java.io.FileNotFoundException;
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

    public double countQ(ArrayList<Integer> data, int k) {
        int size = data.size();
        double i = (k / 100.) * (size + 1);

        return data.get((int) i - 1) + (data.get((int) i) - data.get((int) i - 1)) * (i - (int) i);
    }

    public int getMedian(ArrayList<Integer> sortedData) {
        float relativeCumulativeFrequencies = 0;
        float total = (float) sortedData.size();
        int median = 0;


        for (Integer num : sortedData) {
            relativeCumulativeFrequencies += 1 / total;
            if (relativeCumulativeFrequencies > 0.5) {
                median = num;
                System.out.println("median found by relative cumulative frequency: " + relativeCumulativeFrequencies);
                break;
            }
        }
        return median;
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

    private float varX = 0;
    public float findVarX(LinkedHashMap<Integer, LinkedList<Float>> input, float midX, int numOfData){
        if (varX == 0){
            float midXAbc = midX * midX;
            for (Map.Entry<Integer, LinkedList<Float>> entry : input.entrySet()) {
                for (Float num : entry.getValue()) {
                    varX += num * num - midXAbc;
                }
            }
            varX = varX / numOfData;
        }
        return varX;
    }
    public double getMeanSquareDeviation(double dispersion) {
        return Math.sqrt(dispersion);
    }

    public double countStep(LinkedHashMap<Integer, Integer> data, int count) {
        List<Map.Entry<Integer, Integer>> entries =
                new ArrayList<>(data.entrySet());

        return (entries.get(entries.size() - 1).getKey() - entries.get(0).getKey()) / Math.sqrt(count);
    }

    public double getFixedDispersion(ArrayList<Integer> sortedData) {
        double middle = getMedium(sortedData);

        double absSum = 0;

        for (Integer num : sortedData) {
            absSum += Math.pow(num - middle, 2);
        }

        return absSum / (sortedData.size() - 1);
    }

    public double getMedium(ArrayList<Integer> data) {
        double all = 0;
        for (Integer num : data) {
            all += num;
        }
        return all / data.size();
    }

    public static <T, Q> LinkedHashMap<T, Q> reverseMap(LinkedHashMap<T, Q> toReverse) {
        LinkedHashMap<T, Q> reversedMap = new LinkedHashMap<>();
        List<T> reverseOrderedKeys = new ArrayList<>(toReverse.keySet());
        Collections.reverse(reverseOrderedKeys);
        reverseOrderedKeys.forEach((key) -> reversedMap.put(key, toReverse.get(key)));
        return reversedMap;
    }
}
