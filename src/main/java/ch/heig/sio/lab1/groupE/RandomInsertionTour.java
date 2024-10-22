package ch.heig.sio.lab1.groupE;

import ch.heig.sio.lab1.tsp.TspData;

import java.util.*;

public class RandomInsertionTour extends InsertionTour {

    /**
     * The order of the cities
     */
    ArrayList<Integer> order;

    /**
     * The current index in the order
     */
    int currentIndex = 0;


    /**
     * Iinitialize the tour with a random order of cities
     * @param data TspData
     * @param startCityIndex the start city
     */
    public void init(TspData data, int startCityIndex) {
    currentIndex = 0;
    order = new ArrayList<>(data.getNumberOfCities()-1);
    for (int i = 0; i < data.getNumberOfCities(); i++) {
        if (i != startCityIndex) {
            order.add(i);
        }
    }
    Collections.shuffle(order);
}


    /**
     * Get the next city in the random order
     * @param data TspData
     * @return the index of the next city
     */
    @Override
    public int getNextCityIndex(TspData data) {
        return  order.get(currentIndex++);
    }




}



