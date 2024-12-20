package ch.heig.sio.lab1.groupE;

import ch.heig.sio.lab1.display.HeuristicComboItem;
import ch.heig.sio.lab1.tsp.TspData;
import ch.heig.sio.lab1.tsp.TspTour;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Analyze the performance of different heuristics on different files.
 * The metrics computed are:
 * - Max
 * - Min
 * - Median
 * - Mean
 * - Standard Deviation
 * - Performance (Mean / Optimal Length * 100)
 * The results are printed in a table format in the terminal.
 *
 * @author Rachel Tranchida
 * @author Eva Ray
 */
public final class Analyze {

    // HashMap with optimal lengths for each file
    static HashMap<String, Long> optimalLengths = new HashMap<>(Map.of(
            "pcb442", 50778L,
            "att532", 86729L,
            "u574", 36905L,
            "pcb1173", 56892L,
            "nrw1379", 56638L,
            "u1817", 57201L
    ));

    public static void main(String[] args) {

        // Array of heuristics to compare
        HeuristicComboItem[] heuristics = {
                new HeuristicComboItem("Random Tour", new RandomInsertionTour(0x134DA73)),
                new HeuristicComboItem("Nearest Tour", new NearestInsertionTour()),
                new HeuristicComboItem("Farthest Tour", new FarthestInsertionTour())
        };
        // Array of files to analyze
        String[] files = {"att532", "nrw1379", "pcb442", "pcb1173", "u574", "u1817"};
        // HashMap to store the results for each file
        HashMap<String, HashMap<String, ArrayList<Long>>> results = new HashMap<>();
        // data is used to store the data from the file
        TspData data;
        // After computing all tours, print statistics in a table format
        for (String file : files) {
            try {
                data = TspData.fromFile("data/" + file + ".dat");

                results.put(file, new HashMap<>());

                for (HeuristicComboItem heuristic : heuristics) {

                    results.get(file).put(heuristic.toString(), new ArrayList<>());
                    // Collect lengths of tours for each heuristic
                    for (int i = 0; i < data.getNumberOfCities(); i++) {
                        TspTour tour = heuristic.computeTour(data, i);
                        results.get(file).get(heuristic.toString()).add(tour.length());
                    }
                }
                printTable(results.get(file), file);
                System.out.println();
            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Print statistics for each heuristic for a given file results.
     * The statistics are:
     * - Max
     * - Min
     * - Median
     * - Mean
     * - Standard Deviation
     * - Performance (Mean / Optimal Length * 100)
     * The results are printed in a table format in the terminal.
     *
     * @param heuristicLengths the heuristic name mapped to the distance found for each starting city
     * @param fileName         the file name
     */
    static void printTable(Map<String, ArrayList<Long>> heuristicLengths, String fileName) {
        String[] metrics = {"Max", "Min", "Median", "Mean", "Standard Deviation", "Performance (%)"};
        System.out.println("Statistics for file: " + fileName);
        System.out.printf("%-20s", "Metric");
        for (String heuristic : heuristicLengths.keySet()) {
            System.out.printf("%-20s", heuristic);
        }
        System.out.printf("%-20s\n", "Optimal Tour");

        for (String metric : metrics) {
            System.out.printf("%-20s", metric);
            for (ArrayList<Long> lengths : heuristicLengths.values()) {
                lengths.sort(Long::compareTo);
                double value = switch (metric) {
                    case "Max" -> lengths.getLast();
                    case "Min" -> lengths.getFirst();
                    case "Median" -> median(lengths);
                    case "Mean" -> lengths.stream().mapToLong(Long::longValue).average().orElse(0);
                    case "Standard Deviation" ->
                            Math.sqrt(lengths.stream().mapToDouble(l -> Math.pow(l - median(lengths), 2)).sum() / lengths.size());
                    case "Performance (%)" ->
                            lengths.stream().mapToLong(Long::longValue).average().orElse(0) * 100 / optimalLengths.get(fileName);
                    default -> 0;
                };
                System.out.printf("%-20.2f", value);
            }
            double value = switch (metric) {
                case "Standard Deviation" -> 0;
                case "Performance (%)" -> 100;
                default -> optimalLengths.get(fileName);
            };
            System.out.printf("%-20.2f\n", value);
        }
    }

    /**
     * Compute the median of a sorted list of values.
     *
     * @param sortedValues the sorted list of values
     * @return the median
     */
    public static double median(ArrayList<Long> sortedValues) {
        if (sortedValues.size() % 2 == 1)
            return sortedValues.get((sortedValues.size() + 1) / 2 - 1);
        else {
            double lower = sortedValues.get(sortedValues.size() / 2 - 1);
            double upper = sortedValues.get(sortedValues.size() / 2);
            return (lower + upper) / 2.0;
        }
    }
}

