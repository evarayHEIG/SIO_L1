package ch.heig.sio.lab1.groupE;

import ch.heig.sio.lab1.tsp.TspData;

import java.util.LinkedList;


/**
 * DistanceBaseTour represents a tour that uses an insertion heuristic based on the distance between cities.
 * It extends the InsertionTour class
 * @author Rachel Tranchida
 * @author Eva Ray
 */
public abstract class DistanceBasedTour extends InsertionTour {

    /**
     * The list of candidate cities
     */
    LinkedList<CandidateCity> candidateCities;


    /**
     * Initialize the list of candidate cities with all the cities except the start city
     *
     * @param data           TspData
     * @param startCityIndex the start city
     */
    @Override
    public void init(TspData data, int startCityIndex) {
        super.init(data, startCityIndex);
        candidateCities = new LinkedList<>();
        for (int i = 0; i < data.getNumberOfCities(); i++) {
            if (i != startCityIndex) {
                candidateCities.add(new CandidateCity(startCityIndex, i, data.getDistance(startCityIndex, i)));
            }
        }
    }

    /**
     * Update the distance and city that is closest in the tour for every city outside the tour
     * and delete the city that was added to the tour from the list of cities outside the tour
     *
     * @param data             tsp data
     * @param lastCityInserted The last citx that was inserted in the tour
     * @param candidateCities  The list of cities outside the tour
     */
    public void updateClosestCities(TspData data, int lastCityInserted, LinkedList<CandidateCity> candidateCities) {
        // We remove the city that was just inserted from the list
        candidateCities.removeIf(city -> city.getUnvisitedVertex() == lastCityInserted);

        // We iterate on all the cities outside the tour
        for (var city : candidateCities) {

            // If the distance between the last city inserted and the current city is smaller than the distance
            // between the current city and the city that was the closest in the tour, it means that the current city
            // is now the closest city in the tour. We update the distance and the city that is the closest in the tour.
            if (data.getDistance(lastCityInserted, city.getUnvisitedVertex()) < city.getDistance()) {
                city.setTourVertex(lastCityInserted);
                city.setDistance(data.getDistance(lastCityInserted, city.getUnvisitedVertex()));
            }
        }
    }
}
