package ch.heig.sio.lab1.groupE;

import ch.heig.sio.lab1.display.HeuristicComboItem;
import ch.heig.sio.lab1.tsp.TspData;
import ch.heig.sio.lab1.tsp.TspTour;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class Analyze {

    static HashMap<String, Long> optimalLengths = new HashMap<>(Map.of(
            "pcb442", 50778L,
            "att532", 86729L,
            "u574", 36905L,
            "pcb1173", 56892L,
            "nrw1379", 56638L,
            "u1817", 57201L
    ));

    // pcb442 : 50778
    // att532 : 86729
    // u574 : 36905
    // pcb1173   : 56892
    // nrw1379  : 56638
    // u1817 : 57201

  public static void main(String[] args) {
    // TODO
    //  - Renommer le package ;
    //  - Implémenter les différentes heuristiques en écrivant le code dans ce package, et uniquement celui-ci
    //    (sous-packages et packages de tests ok) ;
    //  - Factoriser le code commun entre les différentes heuristiques ;
    //  - Documentation soignée comprenant :
    //    - la javadoc, avec auteurs et description des implémentations ;
    //    - des commentaires sur les différentes parties de vos algorithmes.
    HeuristicComboItem[] heuristics = {
            new HeuristicComboItem("Random Tour", new RandomInsertionTour(0x134DA73)),
            new HeuristicComboItem("Nearest Tour", new NearestInsertionTour()),
            new HeuristicComboItem("Farthest Tour", new FarthestInsertionTour())
    };

    String[] files = {"att532"/*, "nrw1379", "pcb442", "pcb1173", "u574", "u1817"*/};

    HashMap<String, HashMap<String, ArrayList<Long>>> results = new HashMap<>();
        TspData data = null;
      // After computing all tours, print statistics in a table format
      for (String file : files) {
          try {
              data = TspData.fromFile("data/" + file + ".dat");
          } catch (FileNotFoundException e) {
              e.getMessage();
          }
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
         // System.out.println("Statistics for file: " + file);
          }







    // Longueurs optimales :
    // pcb442 : 50778
    // att532 : 86729
    // u574 : 36905
    // pcb1173   : 56892
    // nrw1379  : 56638
    // u1817 : 57201

    // Exemple de lecture d'un jeu de données :
    // TspData data = TspData.fromFile("data/att532.dat");
  }

  static class Result {
    int startCity;
    Long distance;


    Result(int startCity, Long distance) {
      this.startCity = startCity;
      this.distance = distance;
    }



  }

    static void printStatistics(ArrayList<Long> lengths, String fileName) {
        lengths.sort(Long::compareTo);
        Long max = lengths.getLast();
        Long min = lengths.getFirst();
        double median = median(lengths);
        double mean = lengths.stream().mapToLong(Long::longValue).average().orElse(0);
        double stdDev = Math.sqrt(lengths.stream().mapToDouble(l -> Math.pow(l - mean, 2)).sum() / lengths.size());

        System.out.println("Statistics for file: " + fileName);
        System.out.printf("Max: %d, Min: %d, Median: %.2f, Mean: %.2f, Std Dev: %.2f\n", max, min, median, mean, stdDev);
    }

    static void printTable(Map<String, ArrayList<Long>> heuristicLengths, String fileName) {
        String[] metrics = {"Max", "Min", "Median", "Mean", "Standard Deviation", "Performance"};
        System.out.println("Statistics for file: " + fileName);
        System.out.printf("%-20s", "Metric");
        for (String heuristic : heuristicLengths.keySet()) {
            System.out.printf("%-20s", heuristic);
        }
        System.out.printf("%-20s\n", "Optimal Tour");

        for (String metric : metrics) {
            System.out.printf("%-20s", metric);
            for (ArrayList<Long> lengths : heuristicLengths.values()) {
                double value = switch (metric) {
                    case "Max" -> lengths.getLast();
                    case "Min" -> lengths.getFirst();
                    case "Median" -> median(lengths);
                    case "Mean" -> lengths.stream().mapToLong(Long::longValue).average().orElse(0);
                    case "Standard Deviation" ->
                            Math.sqrt(lengths.stream().mapToDouble(l -> Math.pow(l - median(lengths), 2)).sum() / lengths.size());
                    case "Performance" ->
                            lengths.stream().mapToLong(Long::longValue).average().orElse(0) * 100 / optimalLengths.get(fileName);
                    default -> 0;
                };
                System.out.printf("%-20.2f", value);
            }
            double value = switch (metric) {
                case "Standard Deviation" -> 0;
                case "Performance" -> 100;
                default -> optimalLengths.get(fileName);
            };
            System.out.printf("%-20.2f\n", value);
        }
    }

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

