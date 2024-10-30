package ch.heig.sio.lab1.groupE;

/**
 * A candidate city is a city that is not yet in the tour and that is a candidate to be inserted in the tour
 * It is defined by the index of the city in the tour, the index of the city in the list of unvisited cities and the
 * distance between the city in the tour and the city in the list of unvisited cities
 * It implements the Comparable interface to be able to compare two candidate cities based on their distance.
 * @author Rachel Tranchida
 * @author Eva Ray
 */
public class CandidateCity implements Comparable<CandidateCity> {

    // The index of the city in the tour
    private int tourVertex;
    // The index of the city in the list of unvisited cities
    private int unvisitedVertex;
    // The distance between the city in the tour and the city in the list of unvisited cities
    private double distance;

    /**
     * Constructor for the CandidateCity
     * @param tourVertex the index of the city in the tour
     * @param unvisitedVertex the index of the city in the list of unvisited cities
     * @param distance the distance between the city in the tour and the city in the list of unvisited cities
     */
    public CandidateCity(int tourVertex, int unvisitedVertex, double distance) {
        this.tourVertex = tourVertex;
        this.unvisitedVertex = unvisitedVertex;
        this.distance = distance;
    }

    /**
     * Get the index of the city in the tour
     * @return the index of the city in the tour
     */
    public int getTourVertex() {
        return tourVertex;
    }

    /**
     * Set the index of the city in the tour
     * @param tourVertex the index of the city in the tour
     */
    public void setTourVertex(int tourVertex) {
        this.tourVertex = tourVertex;
    }

    /**
     * Get the index of the city in the list of unvisited cities
     * @return the index of the city in the list of unvisited cities
     */
    public int getUnvisitedVertex() {
        return unvisitedVertex;
    }

    /**
     * Set the index of the city in the list of unvisited cities
     * @param unvisitedVertex the index of the city in the list of unvisited cities
     */
    public void setUnvisitedVertex(int unvisitedVertex) {
        this.unvisitedVertex = unvisitedVertex;
    }

    /**
     * Get the distance between the city in the tour and the city in the list of unvisited cities
     * @return the distance between the city in the tour and the city in the list of unvisited cities
     */
    public double getDistance() {
        return distance;
    }

    /**
     * Set the distance between the city in the tour and the city in the list of unvisited cities
     * @param distance the distance between the city in the tour and the city in the list of unvisited cities
     */
    public void setDistance(double distance) {
        this.distance = distance;
    }

    /**
     * Compare two candidate cities based on their distance
     * @param o the other candidate city
     * @return a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object.
     */
    @Override
    public int compareTo(CandidateCity o) {
        return Double.compare(this.distance, o.distance);
    }
}
