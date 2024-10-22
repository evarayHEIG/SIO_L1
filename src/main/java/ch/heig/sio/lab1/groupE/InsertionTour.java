package ch.heig.sio.lab1.groupE;

import ch.heig.sio.lab1.display.ObservableTspConstructiveHeuristic;
import ch.heig.sio.lab1.display.TspHeuristicObserver;
import ch.heig.sio.lab1.tsp.Edge;
import ch.heig.sio.lab1.tsp.TspData;
import ch.heig.sio.lab1.tsp.TspTour;

public abstract class InsertionTour implements ObservableTspConstructiveHeuristic {


    DoublyLinkedList<Edge> currTour;

    public abstract void init(TspData data, int startCityIndex);
    @Override
    public TspTour computeTour(TspData data, int startCity, TspHeuristicObserver observer) {
        init(data, startCity);
        currTour = new DoublyLinkedList<>();
        long length = 0;
        currTour.addLast(new Edge(startCity, startCity));
        for (int i = 0; i < data.getNumberOfCities()-1; i++) {
            int nextCityIndex = getNextCityIndex(data);
            length += bestInsertion(data, nextCityIndex, currTour);
            observer.update(currTour.iterator());
        }

        return new TspTour(data, fillTour(currTour, data.getNumberOfCities()), length);
    }

    public abstract int getNextCityIndex(TspData data);
    /**
     * Computes the best insertion
     * @param data TspData
     * @param cityToInsert the city to insert in the tour
     * @param currentTour the current tour as a list of edges
     * @return the added length of the insertion
     */
    public long bestInsertion(TspData data, int cityToInsert, DoublyLinkedList<Edge> currentTour) {


        int addedLength;
        int minAddedLength = Integer.MAX_VALUE;
        var minNode = currentTour.head;
        var current = currentTour.head;
        while (current != null) {

            addedLength = data.getDistance(current.data.u(), cityToInsert) + data.getDistance(cityToInsert, current.data.v())
                    - data.getDistance(current.data.u(), current.data.v());
            if (addedLength < minAddedLength) {
                minAddedLength = addedLength;
                minNode = current;
            }
            current = current.next;
        }

        Edge oldEdge = minNode.data;
        // Le edge minimal  (u, v) (data de minNode) pour lequel l'ajout de notre nouveau sommet s entre u et v
        // cause la plus petite augmentation de la longueur du chemin
        // on remplace donc le edge minimal par le edge (u, s) puis on ajoute à la suite de minNode
        // un nouveau node contenant le edge (s, v)
        minNode.data = new Edge(oldEdge.u(), cityToInsert);
        currentTour.addAfter(minNode, new Edge(cityToInsert, oldEdge.v()));

        return minAddedLength;

    }

    public int[] fillTour(DoublyLinkedList<Edge> currentTour, int size) {
        var current = currentTour.head;
        int index = 0;
        int[] tour = new int[size];

        while (current != null) {
            tour[index] = current.data.u();
            current = current.next;
            index++;
        }
        return tour;
    }
}
