package visualization;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;
import simulation.Simulator;
import visualization.model_panels.*;

/**
 * This is the Graphical User Interface for displaying the simulation models.
 * It contains three components:
 *  1. modelPanel that displays the model's parameters
 *  2. simPanel that displays the real-time simulation
 *  3. controlPanel that allows the user to adjust settings
 *
 * The controlPanel contains multiple buttons that will handle events, such as loading/saving settings
 * from XML file, start/pause the simulation, change the rate of simulation, or change the model
 *
 * @author Vincent Liu
 * @author Inchan Hwang
 */
public class GUI {
    public static final int SCREEN_WIDTH = 840;
    public static final int SCREEN_HEIGHT = 726;

    public static final int FRAMES_PER_SECOND = 10;
    public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    public static final double SECOND_DELAY = MILLISECOND_DELAY / 1000.;

    private Window window;
    private GridPane root;
    private SimulationControlPanel simControlPanel;

    private Simulator<?> simulator;

    public GUI () {
        root = new GridPane();
        root.getStyleClass().add("root");

        var column1 = new ColumnConstraints();
        column1.setPercentWidth(20);
        var column2 = new ColumnConstraints();
        column2.setPercentWidth(80);
        root.getColumnConstraints().addAll(column1, column2);
        var row1 = new RowConstraints();
        row1.setPercentHeight(85);
        var row2 = new RowConstraints();
        row2.setPercentHeight(15);
        root.getRowConstraints().addAll(row1, row2);

        modelPanel = new GameOfLifeControl();
        simControlPanel = new SimulationControlPanel(
                simulator,
                e -> handleFileLoad(), e -> handleFileSave(),
                (a, b, c) -> handleModelChange(c),
                (a, b, c) -> handleShapeChange(c)
        );
        simPanel = new VBox();
        simPanel.getStyleClass().add("simPanel");

        root.add(modelPanel, 0, 0);
        root.add(simPanel, 1, 0);
        root.add(simControlPanel, 0, 1, 2, 1);

        initializeSimulation();
    }

    /**
     *
     * @param sim
     */

    /**
     *
     * @param primaryStage
     */
    public void runGUI (Stage primaryStage) {
        window = primaryStage;

        primaryStage.setTitle("Simulations");
        Scene scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT);
        scene.getStylesheets().add("style.css");
        primaryStage.setScene(scene);
        primaryStage.show();

        var frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));
        var animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
    }

    /**
     *
     * @param duration
     */
    public void step(double duration) {
        simControlPanel
    }

    private void handleModelPanelParametersChange() {
        if(modelPanel.isParamChanged()) {
            simulator.updateSimulationModel(modelPanel.getParams());
            modelPanel.cleanParamChanged();
        }
    }
    private void handleCellNumChange() {
        if (modelPanel.isNumCellChanged()) {
            generateModelByName(
                    modelPanel.getCellNum(),
                    simControlPanel.getChosenModel(),
                    simControlPanel.getChosenShape()
            );
            modelPanel.cleanNumCellChanged();
        }
    }
}
