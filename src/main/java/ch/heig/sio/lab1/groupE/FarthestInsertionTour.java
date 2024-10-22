package ch.heig.sio.lab1.groupE;

import ch.heig.sio.lab1.tsp.TspData;

public class FarthestInsertionTour extends DistanceBasedTour {

    @Override
    public int getNextCityIndex(TspData data) {
        int nextCityIndex = getFarthestCity();
        updateClosestCities(data, nextCityIndex, candidateCities);
        return nextCityIndex;
    }

    int getFarthestCity() {
        double maxDist = Double.MIN_VALUE;
        int maxCityIndex = -1;
        for(var city : candidateCities) {
            if(city.getDistance() > maxDist) {
                maxDist = city.getDistance();
                maxCityIndex = city.getUnvisitedVertex();

            }
        }
        return maxCityIndex;
    }
}
