package ch.heig.sio.lab1.groupE;

import ch.heig.sio.lab1.tsp.TspData;

import java.util.*;

/**
 * A constructive heuristic for the TSP, that will compute a tour choosing for each insertion a random city outside
 * the tour. The order of the cities is random but the insertion is chosen by finding the closest city in the tour.
 * It extends InsertionTour.
 * @author Rachel Tranchida
 * @author Eva Ray
 */
public class RandomInsertionTour extends InsertionTour {

    // ArrayList representing the order in which the cities are added to the tour
    ArrayList<Integer> order;
    // The seed for the random number generator
    int seed;
    // The current index in the order list
    int currentIndex = 0;

    /**
     * Default constructor
     */
    public RandomInsertionTour() {
        super();
        this.seed = new Random().nextInt();
    }

    /**
     * Constructor
     *
     * @param seed the seed for the random number generator
     */
    public RandomInsertionTour(int seed) {
        super();
        this.seed = seed;
    }

    /**
     * Iinitialize the tour with a random order of cities
     *
     * @param data           TspData
     * @param startCityIndex the start city
     */
    public void init(TspData data, int startCityIndex) {

        super.init(data, startCityIndex);
        // We (re)set the current index to 0
        currentIndex = 0;
        // We create a list of all the cities except the start city
        order = new ArrayList<>(data.getNumberOfCities() - 1);
        for (int i = 0; i < data.getNumberOfCities(); i++) {
            if (i != startCityIndex) {
                order.add(i);
            }
        }
        Random random = new Random(seed);

        // We shuffle the list, to create a random order of the cities
        Collections.shuffle(order, random);
    }

    /**
     * Get the next city in the random order
     *
     * @param data TspData
     * @return the index of the next city
     */
    @Override
    public int getNextCityIndex(TspData data) {
        return order.get(currentIndex++);
    }
}



