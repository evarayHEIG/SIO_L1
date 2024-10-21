package ch.heig.sio.lab1.groupE;

import ch.heig.sio.lab1.display.ObservableTspConstructiveHeuristic;
import ch.heig.sio.lab1.display.TspHeuristicObserver;
import ch.heig.sio.lab1.tsp.Edge;
import ch.heig.sio.lab1.tsp.TspData;
import ch.heig.sio.lab1.tsp.TspTour;
import org.w3c.dom.Node;

import java.util.*;

public class RandomInsertionTour implements ObservableTspConstructiveHeuristic {


    // Version de computeTour ou on définit une DoublyLinkedList<Edge>
    public TspTour computeTour(TspData data, int startCityIndex, TspHeuristicObserver observer) {
        int n = data.getNumberOfCities();


        // On crée une liste qui stocke l'ordre aléatoire dans lequel les villes seront visitées
        ArrayList<Integer> order = new ArrayList<>(n);
        // DoublyLinkedList<Integer> order = new DoublyLinkedList<>();
        order.add(startCityIndex);
        for (int i = 0; i < n; i++) {
            if (i != startCityIndex) {
                order.add(i);
            }
        }
        Collections.shuffle(order);
        DoublyLinkedList<Edge> tempOrder = new DoublyLinkedList<>();
        tempOrder.addFirst(new Edge(startCityIndex, startCityIndex));


        int[] tour = new int[n];
        long length = 0;

        for (int i = 1; i < n; ++i) {
            int addedLength;
            int minAddedLength = Integer.MAX_VALUE;
            var minNode = tempOrder.head;
            var current = tempOrder.head;
            while (current != null) {

                addedLength = data.getDistance(current.data.u(), order.get(i)) + data.getDistance(order.get(i), current.data.v())
                        - data.getDistance(current.data.u(), current.data.v());
                if (addedLength < minAddedLength) {
                    minAddedLength = addedLength;
                    minNode = current;
                }
                current = current.next;
            }
            length += minAddedLength;
            Edge oldEdge = minNode.data;
            // Le edge minimal  (u, v) (data de minNode) pour lequel l'ajout de notre nouveau sommet s entre u et v
            // cause la plus petite augmentation de la longueur du chemin
            // on remplace donc le edge minimal par le edge (u, s) puis on ajoute à la suite de minNode
            // un nouveau node contenant le edge (s, v)
            minNode.data = new Edge(oldEdge.u(), order.get(i));
            tempOrder.addAfter(minNode, new Edge(order.get(i), oldEdge.v()));
            // on update l'observer pour le graphisme
            observer.update(tempOrder.iterator());
        }

        var current = tempOrder.head;
        int index = 0;
        /*for(;current != null; current = current.next) {
            tour[index] = current.data;
            index++;
        }*/
        while (current != null) {
            tour[index] = current.data.u();
            current = current.next;
            index++;
        }

        return new TspTour(data, tour, length);
    }
}



