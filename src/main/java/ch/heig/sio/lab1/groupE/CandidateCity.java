package ch.heig.sio.lab1.groupE;

import static java.util.Collections.min;

/**
 * A candidate city is a city that is not yet in the tour and that is a candidate to be inserted in the tour
 * It is defined by the index of the city in the tour, the index of the city in the list of unvisited cities and the distance between the city in the tour and the city in the list of unvisited cities
 */
public class CandidateCity implements Comparable<CandidateCity> {

    private int tourVertex;
    private int unvisitedVertex;
    private double distance;

    public int getTourVertex() {
        return tourVertex;
    }

    public void setTourVertex(int tourVertex) {
        this.tourVertex = tourVertex;
    }

    public int getUnvisitedVertex() {
        return unvisitedVertex;
    }

    public void setUnvisitedVertex(int unvisitedVertex) {
        this.unvisitedVertex = unvisitedVertex;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public CandidateCity(int tourVertex, int unvisitedVertex, double distance) {
        this.tourVertex = tourVertex;
        this.unvisitedVertex = unvisitedVertex;
        this.distance = distance;
    }

    @Override
    public int compareTo(CandidateCity o) {
        return Double.compare(this.distance, o.distance);
    }


}
