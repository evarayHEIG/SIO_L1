package ch.heig.sio.lab1.groupE;

import ch.heig.sio.lab1.tsp.TspData;

import java.util.LinkedList;

public class NearestInsertionTour extends DistanceBasedTour {



    public int getClosestCity(LinkedList<CandidateCity> candidateCities) {


        double minDist = Double.MAX_VALUE;
        int minCityIndex = -1;
        // initialisation du tableau des villes les plus proches
        for(var city : candidateCities) {
            if(city.getDistance() < minDist) {
                minDist = city.getDistance();
                minCityIndex = city.getUnvisitedVertex();

            }
        }
        return minCityIndex;
    }





    @Override
    public int getNextCityIndex(TspData data) {
        int nextCityIndex = getClosestCity(candidateCities);
        updateClosestCities(data, nextCityIndex, candidateCities);
        return nextCityIndex;
    }
}
