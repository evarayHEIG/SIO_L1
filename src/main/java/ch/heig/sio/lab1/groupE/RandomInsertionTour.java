package ch.heig.sio.lab1.groupE;

import ch.heig.sio.lab1.display.ObservableTspConstructiveHeuristic;
import ch.heig.sio.lab1.display.TspHeuristicObserver;
import ch.heig.sio.lab1.tsp.Edge;
import ch.heig.sio.lab1.tsp.TspData;
import ch.heig.sio.lab1.tsp.TspTour;
import org.w3c.dom.Node;

import java.util.*;

public class RandomInsertionTour implements ObservableTspConstructiveHeuristic {


    public static class DoublyLinkedList<T> {

        // Attention!!! Itérateur pour l'itérateur de Edge bizarre
       /* public Iterator<Edge> iterator() {

            return new IteratorEdge(head);
        }*/

        // itérateur pour l'itérateur générique
        public Iterator<T> iterator() {
            return new DoublyLinkedListIterator();
        }

        // Iterator qui retourne des Edge si on a une liste chaînée d'integer
        // mais vraiment pas ouf si on garde ça générique (suppose que data est un int)
        /* class IteratorEdge<T> implements Iterator<Edge> {

            DoublyLinkedList.Node<T> current = null;

            public IteratorEdge(DoublyLinkedList.Node<T> first) {
                current = first;
            }

            @Override
            public boolean hasNext() {
                return current.hasNext() && current.next.hasNext();
            }

            @Override
            public Edge next() {

                if (!hasNext()) {
                    throw new NoSuchElementException("No next element");
                }
                current = current.next;
                return new Edge((Integer) current.data, (Integer) current.next.data);
            }
        } */

        class DoublyLinkedListIterator implements Iterator<T> {

            private DoublyLinkedList.Node<T> current;
            public DoublyLinkedListIterator() {
                current = head;
            }
            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                if(!hasNext()) {
                    throw new NoSuchElementException("No next element");
                }
                var old = current;
                current = current.next;
                return old.data;
            }
        }

        Node<T> head;
        Node<T> tail;
        private int size;

        public DoublyLinkedList() {
            head = null;
            tail = null;
            size = 0;
        }

        private static class Node<T> {
            T data;
            Node<T> prev;
            Node<T> next;

            Node(T data) {
                this.data = data;
            }

            public boolean hasNext() {
                return next != null;
            }


            public boolean hasPrev() {
                return prev != null;
            }

            public Node<T> prev() {
                if (!hasPrev()) {
                    throw new NoSuchElementException("No previous element");
                }
                return prev;
            }

            public Node<T> next() {
                if (!hasNext()) {
                    throw new NoSuchElementException("No next element");
                }
                return next;
            }
        }

        public void addAfter(Node<T> node, T value) {

            if (node.next == null) {
                addLast(value);
            } else {
                Node<T> newNode = new Node<>(value);
                node.next.prev = newNode;
                newNode.next = node.next;
                newNode.prev = node;
                node.next = newNode;
                size++;
            }
        }

        public void addFirst(T data) {
            Node<T> newNode = new Node<>(data);
            if (head == null) {
                head = newNode;
                tail = newNode;
            } else {
                newNode.next = head;
                head.prev = newNode;
                head = newNode;
            }
            size++;
        }

        public void addLast(T data) {
            Node<T> newNode = new Node<>(data);
            if (tail == null) {
                head = newNode;
            } else {
                newNode.prev = tail;
                tail.next = newNode;
            }
            tail = newNode;
            size++;
        }

        public T removeFirst() {
            if (head == null) {
                throw new NoSuchElementException();
            }
            T data = head.data;
            head = head.next;
            if (head == null) {
                tail = null;
            } else {
                head.prev = null;
            }
            size--;
            return data;
        }

        public T removeLast() {
            if (tail == null) {
                throw new NoSuchElementException();
            }
            T data = tail.data;
            tail = tail.prev;
            if (tail == null) {
                head = null;
            } else {
                tail.next = null;
            }
            size--;
            return data;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder("[");
            Node<T> current = head;
            while (current != null) {
                sb.append(current.data);
                if (current.next != null) {
                    sb.append(", ");
                }
                current = current.next;
            }
            sb.append("]");
            return sb.toString();
        }

    }

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



