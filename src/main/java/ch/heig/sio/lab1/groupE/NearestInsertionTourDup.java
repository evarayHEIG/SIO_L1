package ch.heig.sio.lab1.groupE;

import ch.heig.sio.lab1.display.ObservableTspConstructiveHeuristic;
import ch.heig.sio.lab1.display.TspHeuristicObserver;
import ch.heig.sio.lab1.tsp.Edge;
import ch.heig.sio.lab1.tsp.TspData;
import ch.heig.sio.lab1.tsp.TspTour;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class NearestInsertionTourDup extends DistanceBasedTour {




    @Override
    public int getNextCityIndex(TspData data, Set<Integer> unvisited, ArrayList<CandidateCity> candidateCities) {
        int nextCityIndex = getClosestCity(unvisited, candidateCities);
        updateClosestCities(data, nextCityIndex, unvisited, candidateCities);
        return nextCityIndex;
    }

    public int getClosestCity(Set<Integer> unvisited, ArrayList<CandidateCity> candidateCities) {


        double minDist = Double.MAX_VALUE;
        int minCityIndex = -1;
        // initialisation du tableau des villes les plus proches
        for(var city : unvisited) {
            if(candidateCities.get(city).getDistance() < minDist) {
                minDist = candidateCities.get(city).getDistance();
                minCityIndex = city;

            }
        }
        return minCityIndex;
    }

    // Soit on utilise le Set unvisited et tableau, soit liste chaînée je dirais
    public void updateClosestCities(TspData data, int lastCityInserted, Set<Integer> unvisited,ArrayList<CandidateCity> candidateCities) {
        // on enlève la ville du tour du set des villes non visitées
        unvisited.remove(lastCityInserted);
        for(var city : unvisited) {

            if(data.getDistance(lastCityInserted, candidateCities.get(city).getUnvisitedVertex()) < candidateCities.get(city).getDistance()) {
                candidateCities.get(city).setTourVertex(lastCityInserted);
                candidateCities.get(city).setDistance(data.getDistance(lastCityInserted, candidateCities.get(city).getUnvisitedVertex()));
            }

        }
    }


}
