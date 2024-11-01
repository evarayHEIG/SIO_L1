package ch.heig.sio.lab1.groupE;

import ch.heig.sio.lab1.tsp.TspData;

/**
 * A constructive heuristic for the TSP, that will compute a tour choosing for each
 * insertion the city outside the tour such that its closest city in the tour is the farthest.
 * It extends DistanceBasedTour.
 * @author Rachel Tranchida
 * @author Eva Ray
 */
public class FarthestInsertionTour extends DistanceBasedTour {

    /**
     * Get the farthest city from the current tour and update the closest cities
     *
     * @return the index of the farthest city
     */
    @Override
    public int getNextCityIndex(TspData data) {
        int nextCityIndex = getFarthestCity();
        updateClosestCities(data, nextCityIndex, candidateCities);
        return nextCityIndex;
    }

    /**
     * Get the farthest city from the current tour
     *
     * @return the index of the farthest city
     */
    private int getFarthestCity() {
        // maxDist is the maximum distance between a city outside the tour and the closest city in the tour
        double maxDist = Double.MIN_VALUE;
        // maxCityIndex is the index of the city outside the tour that is the farthest from the closest city in the tour
        int maxCityIndex = -1;
        // We iterate on all the cities outside the tour to find the farthest city
        for (var city : candidateCities) {
            if (city.getDistance() > maxDist) {
                maxDist = city.getDistance();
                maxCityIndex = city.getUnvisitedVertex();

            }
        }
        return maxCityIndex;
    }
}
