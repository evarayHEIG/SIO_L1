package ch.heig.sio.lab1.groupE;

import ch.heig.sio.lab1.display.TspHeuristicObserver;
import ch.heig.sio.lab1.tsp.Edge;
import ch.heig.sio.lab1.tsp.TspData;
import ch.heig.sio.lab1.tsp.TspTour;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public abstract class DistanceBasedTour extends InsertionTour {

    LinkedList<CandidateCity> candidateCities;


    @Override
    public void init(TspData data, int startCityIndex) {
        candidateCities = new LinkedList<>();
        for(int i = 0; i < data.getNumberOfCities(); i++) {
            if(i != startCityIndex) {
                candidateCities.add(new CandidateCity(startCityIndex, i, data.getDistance(startCityIndex, i)));
            }
        }
    }

    public void updateClosestCities(TspData data, int lastCityInserted, LinkedList<CandidateCity> candidateCities) {
        // on enlève la ville du tour du set des villes non visitées
        candidateCities.removeIf(city -> city.getUnvisitedVertex() == lastCityInserted);
        for(var city : candidateCities) {

            if(data.getDistance(lastCityInserted, city.getUnvisitedVertex()) < city.getDistance()) {
                city.setTourVertex(lastCityInserted);
                city.setDistance(data.getDistance(lastCityInserted, city.getUnvisitedVertex()));
            }

        }
    }


}
