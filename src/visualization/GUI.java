package visualization;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import simulation.CellGraph;
import simulation.factory.GameOfLife;
import simulation.factory.Segregation;
import simulation.factory.SpreadingFire;
import simulation.models.GameOfLifeModel;
import simulation.models.SegregationModel;
import simulation.models.SpreadingFireModel;
import visualization.model_panels.GameOfLifePanel;
import visualization.model_panels.ModelPanel;

/**
 * This is the Graphical User Interface for displaying the simulation models.
 * It contains three components:
 *  1. modelPanel that displays model name and specific parameters
 *  2. simPanel that displays the real-time simulation
 *  3. controlPanel that allows the user to adjust settings
 *
 * The controlPanel contains multiple buttons that will handle events, such as loading settings from XML file, start/pause
 * the simulation, or change the rate of simulation
 *
 * @author Vincent Liu
 */
public class GUI {
    public static final int SCREEN_WIDTH = 700;
    public static final int SCREEN_HEIGHT = 550;

    public static final int FRAMES_PER_SECOND = 10;
    public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    public static final double SECOND_DELAY = MILLISECOND_DELAY / 1000.;

    public static final String[] SIMULATION_MODELS = new String[] {
            GameOfLifeModel.MODEL_NAME,
            SegregationModel.MODEL_NAME,
            "Wa-Tor",
            SpreadingFireModel.MODEL_NAME
    };

    private GridPane root;
    private SimulationControlPanel simControlPanel;
    private ModelPanel modelPanel;
    private VBox simPanel;

    private CellGraph<?> graph;

    public GUI () {
        root = new GridPane();

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

        root.setPadding(new Insets(15,15,15,15));
        root.setVgap(10);
        root.setHgap(10);

        // add three major layouts
        modelPanel = new GameOfLifePanel();
        simControlPanel = new SimulationControlPanel(graph);
        simPanel = new VBox();
        simPanel.setStyle("-fx-border-color: black;\n");

        // add the three major layouts
        root.add(modelPanel, 0, 0);
        root.add(simPanel, 1, 0);
        root.add(simControlPanel, 0, 1, 2, 1);

        initializeSimulation(GameOfLife.generate());
    }

    private void initializeSimulation(CellGraph<?> cg) {
        graph = cg;
        simControlPanel.reset(cg);
        simPanel.getChildren().clear();
        simPanel.getChildren().add(cg.view());

        // TODO: GET model parameters and put it into modelPanel
    }

    public void runGUI (Stage primaryStage) {
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

    public void step(double duration) {
        if(simControlPanel.canTick(duration)) graph.tick();
        simControlPanel.setNumTick(graph.tickCount());
        handleModelChange();
    }

    public void handleModelChange() {
        var modelName = simControlPanel.getChosenModel();
        if(graph.modelName().equals(modelName)) return;

        if(modelName.equals(GameOfLifeModel.MODEL_NAME)) {
            initializeSimulation(GameOfLife.generate());
        } else if(modelName.equals(SegregationModel.MODEL_NAME)) {
            initializeSimulation(Segregation.generate());
        } else if(modelName.equals(SpreadingFireModel.MODEL_NAME)) {
            initializeSimulation(SpreadingFire.generate());
        }
    }
}
