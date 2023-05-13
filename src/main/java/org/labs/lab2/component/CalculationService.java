package org.labs.lab2.component;

import java.util.ArrayList;

public class CalculationService {
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

    public double getMeanSquareDeviation(double dispersion) {
        return Math.sqrt(dispersion);
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

    public double getA(double middle) {
        return (95 - 100) / (middle - 100);
    }

    public double getB(double a) {
        return 100 - 100 * a;
    }

    public ArrayList<Float> scaleGrades(double a, double b, ArrayList<Integer> input) {
        ArrayList<Float> scaled = new ArrayList<>();

        for (Integer num : input) {
            scaled.add((float) (num * a + b));
        }

        return scaled;
    }
}
