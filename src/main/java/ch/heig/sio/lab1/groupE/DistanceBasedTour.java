package ch.heig.sio.lab1.groupE;

import ch.heig.sio.lab1.display.TspHeuristicObserver;
import ch.heig.sio.lab1.tsp.Edge;
import ch.heig.sio.lab1.tsp.TspData;
import ch.heig.sio.lab1.tsp.TspTour;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public abstract class DistanceBasedTour extends InsertionTour {

    public abstract int getNextCityIndex(TspData data, Set<Integer> unvisited, ArrayList<CandidateCity> candidateCities);

    @Override
    public TspTour computeTour(TspData data, int startCityIndex, TspHeuristicObserver observer) {
        ArrayList<CandidateCity> candidateVertices = new ArrayList<>();
        Set<Integer> unvisitedVertices = new HashSet<>();
        long length = 0;
        for(int i = 0; i < data.getNumberOfCities(); i++) {

                unvisitedVertices.add(i);
                candidateVertices.add(new CandidateCity(startCityIndex, i, data.getDistance(startCityIndex, i)));

        }
        unvisitedVertices.remove(startCityIndex);
        DoublyLinkedList<Edge> currentTour = new DoublyLinkedList<>();
        currentTour.addLast(new Edge(startCityIndex, startCityIndex));

        for(int i = 0; i < data.getNumberOfCities()-1; i++) {
            int nextCity = getNextCityIndex(data, unvisitedVertices, candidateVertices);
            length += bestInsertion(data, nextCity, currentTour);
            observer.update(currentTour.iterator());
        }

        return new TspTour(data, fillTour(currentTour, data.getNumberOfCities()), length);
    }
}
