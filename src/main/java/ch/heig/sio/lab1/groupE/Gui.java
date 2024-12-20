package ch.heig.sio.lab1.groupE;

import ch.heig.sio.lab1.display.HeuristicComboItem;
import ch.heig.sio.lab1.display.TspSolverGui;
import ch.heig.sio.lab1.sample.CanonicalTour;
import com.formdev.flatlaf.FlatLightLaf;

/**
 * Main class to launch the TSP solver GUI
 * @author Rachel Tranchida
 * @author Eva Ray
 */
public final class Gui {
    public static void main(String[] args) {
        HeuristicComboItem[] heuristics = {
                new HeuristicComboItem("Canonical tour", new CanonicalTour()),
                new HeuristicComboItem("Random Tour", new RandomInsertionTour()),
                new HeuristicComboItem("Nearest Tour", new NearestInsertionTour()),
                new HeuristicComboItem("Farthest Tour", new FarthestInsertionTour())
        };

        // May not work on all platforms, comment out if necessary
        System.setProperty("sun.java2d.opengl", "true");

        FlatLightLaf.setup();
        new TspSolverGui(1400, 800, "TSP solver", heuristics);
    }
}
