package ch.heig.sio.lab1.tsp;

/**
 * Represents an edge between two cities.
 * @param u First city
 * @param v Second city
 */
public record Edge(int u, int v) {

    /**
     * Returns the string representation of the edge.
     * @return The string representation of the edge
     */
    public String toString(){
        return "(" + u + ", " + v + ")";
    }
}
