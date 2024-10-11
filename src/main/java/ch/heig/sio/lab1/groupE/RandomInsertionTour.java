package ch.heig.sio.lab1.groupE;

import ch.heig.sio.lab1.display.ObservableTspConstructiveHeuristic;
import ch.heig.sio.lab1.display.TspHeuristicObserver;
import ch.heig.sio.lab1.tsp.Edge;
import ch.heig.sio.lab1.tsp.TspData;
import ch.heig.sio.lab1.tsp.TspTour;

import java.util.*;

public class RandomInsertionTour implements ObservableTspConstructiveHeuristic {

    private Random random = new Random();

    private static final class Node {
        int city;
        Node next;
        Node prev;

        Node(int city) {
            this.city = city;
        }
    }

    private static final class CustomLinkedList {
        Node head;
        Node tail;
        int size;

        CustomLinkedList() {
            head = new Node(-1);
            tail = new Node(-1);
            head.next = tail;
            tail.prev = head;
            size = 0;
        }

        void addFirst(int city) {

            Node newNode = new Node(city);
            newNode.next = head.next;
            newNode.prev = head;
            head.next.prev = newNode;
            head.next = newNode;
            size++;
        }

        void addLast(int city) {
            Node newNode = new Node(city);
            newNode.prev = tail.prev;
            newNode.next = tail;
            tail.prev.next = newNode;
            tail.prev = newNode;
            size++;
        }

        void addAfter(Node node, int city) {
            Node newNode = new Node(city);
            newNode.next = node.next;
            newNode.prev = node;
            node.next.prev = newNode;
            node.next = newNode;
            size++;
        }

        Node getFirst() {
            return head.next;
        }

        Node getLast() {
            return tail.prev;
        }
    }

    @Override
    public TspTour computeTour(TspData data, int startCityIndex, TspHeuristicObserver observer) {
        int n = data.getNumberOfCities();


        // On crée une liste qui stocke l'ordre aléatoire dans lequel les villes seront visitées
        ArrayList<Integer> order = new ArrayList<>(n);
        order.add(startCityIndex);
        for (int i = 0; i < n; i++) {
            if(i != startCityIndex) {
                order.add(i);
            }
        }
        Collections.shuffle(order);
        LinkedList<Integer> tempTour = new LinkedList<>();

        // Edge[] edges = new Edge[n-1];
        int[] tour = new int[n];
        long length = 0;


        for(int i = 0; i < n; ++i) {



        }

        return null;
    }



}
