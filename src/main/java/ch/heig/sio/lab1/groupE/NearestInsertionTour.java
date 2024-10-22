package ch.heig.sio.lab1.groupE;

import ch.heig.sio.lab1.tsp.TspData;

import java.util.LinkedList;

public class NearestInsertionTour extends DistanceBasedTour {


   /**
     * Get the closest city from the current tour
     * @param candidateCities the list of candidate cities
     * @return the index of the closest city
     */
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




    /**
     * Get the closest city from the current tour and update the closest cities
     * @return the index of the closest city
     */
    @Override
    public int getNextCityIndex(TspData data) {
        int nextCityIndex = getClosestCity(candidateCities);
        updateClosestCities(data, nextCityIndex, candidateCities);
        return nextCityIndex;
    }
}
