package visualization;

import javafx.scene.layout.VBox;
import simulation.Simulator;

/**
 * SimulationPanel is a wrapper for the simulator view
 *
 * @author Inchan Hwang
 * @param <T>
 */
public class SimulationPanel<T> extends VBox {
    private Simulator<T> simulator;

    /**
     * Initialize a customized VBox and add the simulator in it.
     *
     * @param sim
     */
    public SimulationPanel(Simulator<T> sim) {
        getStyleClass().add("simPanel");
        simulator = sim;
        getChildren().add(simulator.view());
    }

    /**
     * Get the simulator from the SimulationPanel
     *
     * @return
     */
    public Simulator<T> simulator() { return simulator; }
}