package labs.lab2.component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
}
