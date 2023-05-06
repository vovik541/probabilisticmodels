package org.labs.lab1.component;

import java.util.*;

public class CalculationService {

    public LinkedHashMap<Integer, Integer> sortData(HashMap<Integer, Integer> data) {
        List<Map.Entry<Integer, Integer>> entries =
                new ArrayList<>(data.entrySet());
        Collections.sort(entries, Map.Entry.comparingByKey());
        LinkedHashMap<Integer, Integer> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<Integer, Integer> entry : entries) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }

    public int getMostViewed(Map<Integer, Integer> data) {
        List<Map.Entry<Integer, Integer>> entries =
                new ArrayList<>(data.entrySet());
        Collections.sort(entries, Map.Entry.comparingByValue());

        return entries.get(entries.size() - 1).getKey();
    }

    public int getMedian(LinkedHashMap<Integer, Integer> sortedData, int totalCount) {
        List<Map.Entry<Integer, Integer>> entries =
                new ArrayList<>(sortedData.entrySet());
        float relativeCumulativeFrequencies = 0;
        float total = (float) totalCount;
        int median = 0;


        for (Map.Entry<Integer, Integer> entry : sortedData.entrySet()) {
            relativeCumulativeFrequencies += entry.getValue() / total;
            if (relativeCumulativeFrequencies > 0.5) {
                median = entry.getKey();
                System.out.println("median found by relative cumulative frequency: " + relativeCumulativeFrequencies);
                break;
            }
        }
        return median;
    }

    public double getDispersion(LinkedHashMap<Integer, Integer> sortedData, int totalCount) {
        double sampleMean;
        double sumXN = 0;
        double sumXXN = 0;
        double dispersion;

        for (Map.Entry<Integer, Integer> entry : sortedData.entrySet()) {
            sumXN += entry.getKey() * entry.getValue();
            sumXXN += Math.pow(entry.getKey(), 2) * entry.getValue();
        }
        sampleMean = sumXN / totalCount;

        dispersion = sumXXN / totalCount - Math.pow(sampleMean, 2);

        return dispersion;
    }

    public double getMeanSquareDeviation(double dispersion) {
        return Math.sqrt(dispersion);
    }

    public LinkedHashMap<Integer, Integer> buildHistogram(LinkedHashMap<Integer, Integer> data, int count) {
        double h = countStep(data, count);
        double currentStep = 0;
        int currentStepNumber = 0;

        LinkedHashMap<Integer, Integer> histogram = new LinkedHashMap<>();

        for (Map.Entry<Integer, Integer> entry : data.entrySet()) {
            if (entry.getKey() < currentStep) {
                if (histogram.containsKey(currentStepNumber)) {
                    histogram.put(currentStepNumber, histogram.get(currentStepNumber) + entry.getValue());
                } else {
                    histogram.put(currentStepNumber, 1);
                }
            } else {
                while (entry.getKey() > currentStep){
                    currentStep += h;
                    currentStepNumber++;
                }
                histogram.put(currentStepNumber, entry.getValue());
            }

        }
        return histogram;
    }

    public double countStep(LinkedHashMap<Integer, Integer> data, int count){
        List<Map.Entry<Integer, Integer>> entries =
                new ArrayList<>(data.entrySet());

        return  (entries.get(entries.size() - 1).getKey() - entries.get(0).getKey()) / Math.sqrt(count);
    }
}
