package ch.heig.sio.lab1.groupE;

import ch.heig.sio.lab1.tsp.TspData;

import java.util.LinkedList;

/**
 * A constructive heuristic for the TSP, that will compute a tour choosing for each
 * insertion the city outside the tour such that its closest city in the tour is the nearest.
 * It extends DistanceBasedTour.
 * @author Rachel Tranchida
 * @author Eva Ray
 */
public class NearestInsertionTour extends DistanceBasedTour {

    /**
     * Get the closest city from the current tour and update the closest cities
     *
     * @return the index of the closest city
     */
    @Override
    public int getNextCityIndex(TspData data) {
        int nextCityIndex = getClosestCity(candidateCities);
        updateClosestCities(data, nextCityIndex, candidateCities);
        return nextCityIndex;
    }

    /**
     * Get the closest city from the current tour
     *
     * @param candidateCities the list of candidate cities
     * @return the index of the closest city
     */
    private int getClosestCity(LinkedList<CandidateCity> candidateCities) {

        // minDist is the minimum distance between a city outside the tour and the closest city in the tour
        double minDist = Double.MAX_VALUE;
        // minCityIndex is the index of the city outside the tour that is the closest from the closest city in the tour
        int minCityIndex = -1;
        // We iterate on all the cities outside the tour to find the closest city
        for (var city : candidateCities) {
            if (city.getDistance() < minDist) {
                minDist = city.getDistance();
                minCityIndex = city.getUnvisitedVertex();
            }
        }
        return minCityIndex;
    }

}
