package visualization;

import javafx.scene.layout.VBox;
import simulation.Simulator;

public class SimulationPanel<T> extends VBox {
    private Simulator<T> simulator;

    public SimulationPanel(Simulator<T> sim) {
        getStyleClass().add("simPanel");
        simulator = sim;
        getChildren().add(simulator.view());
    }

    public Simulator<T> simulator() { return simulator; }
}
