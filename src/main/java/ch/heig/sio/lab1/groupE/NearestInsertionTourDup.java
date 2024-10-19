package ch.heig.sio.lab1.groupE;

import ch.heig.sio.lab1.display.ObservableTspConstructiveHeuristic;
import ch.heig.sio.lab1.display.TspHeuristicObserver;
import ch.heig.sio.lab1.tsp.TspData;
import ch.heig.sio.lab1.tsp.TspTour;

import java.util.Set;

public class NearestInsertionTourDup implements ObservableTspConstructiveHeuristic {


    @Override
    public TspTour computeTour(TspData data, int startCityIndex, TspHeuristicObserver observer) {
        DoublyLinkedList<CandidateCity> closestVertices = new DoublyLinkedList<>();

        for(int i = 0; i < data.getNumberOfCities(); i++) {

            closestVertices.addLast(new CandidateCity(startCityIndex, i, data.getDistance(startCityIndex, i)));

        }


        return null;
    }

    public void getClosestCity(Set<Integer> unvisited, CandidateCity[] candidateCities) {


        double minDist = Double.MAX_VALUE;
        // initialisation du tableau des villes les plus proches
        for(var city : unvisited) {
            if(candidateCities[city].getDistance() < minDist) {
                minDist = candidateCities[city].getDistance();

            }
        }
    }

    // Soit on utilise le Set unvisited et tableau, soit liste chaînée je dirais
    public void updateClosestCities(TspData data, int lastCityInserted, Set<Integer> unvisited, CandidateCity[] candidateCities) {
        // on enlève la ville du tour du set des villes non visitées
        unvisited.remove(lastCityInserted);
        for(var city : unvisited) {

            if(data.getDistance(lastCityInserted, candidateCities[city].getUnvisitedVertex()) < candidateCities[city].getDistance()) {
                candidateCities[city].setTourVertex(lastCityInserted);
                candidateCities[city].setDistance(data.getDistance(lastCityInserted, candidateCities[city].getUnvisitedVertex()));
            }

        }
    }


}
