package ch.heig.sio.lab1;

import ch.heig.sio.lab1.groupE.FarthestInsertionTour;
import ch.heig.sio.lab1.groupE.NearestInsertionTour;
import ch.heig.sio.lab1.tsp.TspConstructiveHeuristic;
import org.junit.jupiter.api.Test;

import static ch.heig.sio.lab1.TestUtils.*;

public class TspHeuristicTest {

    @Test
    public void testOneCity() {
        TspConstructiveHeuristic[] heuristics = getHeuristics();
        testAll(heuristics, ONE_CITY);
    }

    @Test
    public void testTwoCities() {
        TspConstructiveHeuristic[] heuristics = getHeuristics();
        testAll(heuristics, TWO_CITIES);
    }

    @Test
    public void testThreeCities() {
        TspConstructiveHeuristic[] heuristics = getHeuristics();
        testAll(heuristics, THREE_CITIES);
    }

    @Test
    public void testExercise1NearestInsertion() {
        test(new NearestInsertionTour(), EXERCISE_1_NI);
    }

    @Test
    public void testExercise1FarthestInsertion() {
        test(new FarthestInsertionTour(), EXERCISE_1_FI);
    }

    private TspConstructiveHeuristic[] getHeuristics() {
        return new TspConstructiveHeuristic[]{
                new NearestInsertionTour(),
                new FarthestInsertionTour()
        };
    }
}