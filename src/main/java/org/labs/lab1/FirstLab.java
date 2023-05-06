package org.labs.lab1;

import org.labs.lab1.component.CalculationService;
import org.labs.lab1.component.FileManager;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class FirstLab {
    public static void main(String[] args) {
        FileManager fileManager = new FileManager();
        CalculationService service = new CalculationService();

        HashMap<Integer, Integer> input = fileManager.readDataFromFile();
        LinkedHashMap<Integer, Integer> sorted = service.sortData(input);
        fileManager.saveFrequencyTable(sorted);

        int mostViewedFilm = service.getMostViewed(sorted);
        fileManager.saveMostViewed(mostViewedFilm);
        fileManager.saveMode(mostViewedFilm);

        int numberOfData = fileManager.readFirstNumberFromFile();
        int median = service.getMedian(sorted, numberOfData);
        fileManager.saveMedian(median);

        double dispersion = service.getDispersion(sorted, numberOfData);
        fileManager.saveDispersion(dispersion);
        double meanSquareDeviation = service.getMeanSquareDeviation(dispersion);
        fileManager.saveMeanSquareDeviation(meanSquareDeviation);

        LinkedHashMap<Integer, Integer> histogram = service.buildHistogram(sorted, numberOfData);
        double step = service.countStep(sorted, numberOfData);
        fileManager.saveHistogram(histogram, step);


    }
}