package ch.heig.sio.lab1.groupE;

import ch.heig.sio.lab1.display.ObservableTspConstructiveHeuristic;
import ch.heig.sio.lab1.display.TspHeuristicObserver;
import ch.heig.sio.lab1.tsp.Edge;
import ch.heig.sio.lab1.tsp.TspData;
import ch.heig.sio.lab1.tsp.TspTour;

/**
 * Abstract class that represents an insertion tour.
 * It implements the ObservableTspConstructiveHeuristic interface.
 * @author Rachel Tranchida
 * @author Eva Ray
 */
public abstract class InsertionTour implements ObservableTspConstructiveHeuristic {

    /**
     * The current tour
     */
    CustomLinkedList<Edge> currTour;

    /**
     * Initializes the tour
     *
     * @param data           TspData
     * @param startCityIndex the start city
     */
    public void init(TspData data, int startCityIndex) {
        // We initialize the current tour
        currTour = new CustomLinkedList<>();
    }

    /**
     * Computes the tour
     *
     * @param data      TspData
     * @param startCity the start city
     * @param observer  the observer
     * @return the tour
     */
    @Override
    public TspTour computeTour(TspData data, int startCity, TspHeuristicObserver observer) {
        // We initialize the tour
        init(data, startCity);

        // We initialize the length of the tour
        long length = 0;
        // We add the startCity of the tour first
        currTour.addFirst(new Edge(startCity, startCity));
        // We iterate on all the cities except the start city
        for (int i = 0; i < data.getNumberOfCities() - 1; i++) {
            // We retrieve the next city to insert
            int nextCityIndex = getNextCityIndex(data);
            // We compute the best insertion in the tour fot the next city and add the added length
            // that this insertion costs to the total length of the tour
            length += bestInsertion(data, nextCityIndex);
            // We notify the observer of the progress
            observer.update(currTour.iterator());
        }

        return new TspTour(data, fillTour(), length);
    }

    /**
     * Computes the next city to insert
     *
     * @param data the TspData
     * @return the index of the next city to insert
     */
    protected abstract int getNextCityIndex(TspData data);

    /**
     * Computes the best insertion
     *
     * @param data         TspData
     * @param cityToInsert the city to insert in the tour
     * @return the added length of the insertion
     */
    private long bestInsertion(TspData data, int cityToInsert) {
        // addedLength is the length that the insertion of the cityToInsert in the tour costs
        int addedLength;
        // minAddedLength is the minimum length that the insertion of the cityToInsert in the tour costs
        int minAddedLength = Integer.MAX_VALUE;
        // minNode is the node in the tour where the cityToInsert will be inserted
        var minNode = currTour.head;
        // current is the current node in the tour that is being considered for the insertion of the cityToInsert
        var current = currTour.head;
        while (current != null) {

            // We compute the added length of the insertion of the cityToInsert between the current node and the next node
            addedLength = data.getDistance(current.data.u(), cityToInsert) + data.getDistance(cityToInsert, current.data.v())
                    - data.getDistance(current.data.u(), current.data.v());
            // If the added length is less than the minimum added length, we update the minimum added length and the
            // node where the cityToInsert will be inserted
            if (addedLength < minAddedLength) {
                minAddedLength = addedLength;
                minNode = current;
            }
            current = current.next;
        }
        // oldEdge (u, v) is the edge that will be replaced by the two edges (u, cityToInsert) and (cityToInsert, v)
        // because the insertion of the cityToInsert between u and v costs the minimum added length
        Edge oldEdge = minNode.data;
        // We update the edge (u, v) to (u, cityToInsert) and we insert the new edge (cityToInsert, v) after the node
        // minNode. This replaces the edge (u, v) by the two edges (u, cityToInsert) and (cityToInsert, v) in the tour
        minNode.data = new Edge(oldEdge.u(), cityToInsert);
        currTour.addAfter(minNode, new Edge(cityToInsert, oldEdge.v()));

        return minAddedLength;
    }

    /**
     * Fills the array with integers representing the tour
     *
     * @return the tour as an array of integers
     */
    private int[] fillTour() {
        var current = currTour.head;
        int index = 0;
        int[] tour = new int[currTour.getSize()];

        // We iterate on the current tour and fill the array with the cities in the tour
        while (current != null) {
            tour[index] = current.data.u();
            current = current.next;
            index++;
        }
        return tour;
    }
}
