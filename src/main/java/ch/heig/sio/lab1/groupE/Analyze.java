package ch.heig.sio.lab1.groupE;

import ch.heig.sio.lab1.display.HeuristicComboItem;
import ch.heig.sio.lab1.tsp.TspData;
import ch.heig.sio.lab1.tsp.TspTour;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class Analyze {
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

    String[] files = {"att532", "nrw1379", "pcb442", "pcb1173", "u574", "u1817"};

    ArrayList<Result> results = new ArrayList<>();

    for (String file : files) {
        TspData data = null;
        try {
            data = TspData.fromFile("data/" + file + ".dat");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        for (HeuristicComboItem heuristicItem : heuristics) {

        for (int startCity = 0; startCity < data.getNumberOfCities(); startCity++) {
          TspTour tour = heuristicItem.computeTour(data, startCity);
          results.add(new Result(heuristicItem.toString(), startCity, tour.length()));

        }
      }
      writeResultsToFile(results, "csv/" + file + ".csv");

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

  private static void writeResultsToFile(List<Result> results, String fileName) {
    try (FileWriter writer = new FileWriter(fileName)) {
      writer.write("Heuristic,Start City,Distance\n");
      for (Result result : results) {
        writer.write(result.toString() + "\n");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  static class Result {
    String heuristicName;
    int startCity;
    Long distance;


    Result(String heuristicName, int startCity, Long distance) {
      this.heuristicName = heuristicName;
      this.startCity = startCity;
      this.distance = distance;
    }


    public String toString() {
      return  heuristicName + "," + startCity + "," + distance;
    }
  }
}
