package visualization;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import simulation.CellGraph;
import simulation.factory.GameOfLife;
import simulation.factory.Segregation;
import simulation.rules.GameOfLifeRule;
import simulation.rules.SegregationRule;

/**
 * This is the Graphical User Interface for displaying the simulation models.
 * It contains three components:
 *  1. modelPanel that displays model name and specific parameters
 *  2. simulationPanel that displays the real-time simulation
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
            GameOfLifeRule.MODEL_NAME,
            SegregationRule.MODEL_NAME,
            "Wa-Tor",
            "Spreading Fire"
    };

    private GridPane root;
    private VBox simulationPanel, modelPanel;
    private Text numSim, stepRate, modelName;
    private ComboBox<String> chooseModel;

    private CellGraph<?> graph;
    private boolean isPlaying;
    private double simPeriod, elapsedTime;

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
        var model_panel_text = new Text("Model Panel");
        modelPanel = new VBox();
        modelPanel.setStyle("-fx-border-color: black;\n");
        modelPanel.getChildren().add(model_panel_text);
        modelPanel.setSpacing(25);

        simulationPanel = new VBox();
        simulationPanel.setStyle("-fx-border-color: black;\n");
        initializeSimulation(GameOfLife.generate());

        var controlPanel = new HBox();
        controlPanel.setStyle("-fx-border-color: black;\n");
        controlPanel.setSpacing(25);

        controlPanel.getChildren().add(simulationControllers());
        // add the three major layouts
        root.add(modelPanel, 0, 0);
        root.add(simulationPanel, 1, 0);
        root.add(controlPanel, 0, 1, 2, 1);
    }

    private void initializeSimulation(CellGraph<?> cg) {
        graph = cg;
        simulationPanel.getChildren().clear();
        simulationPanel.getChildren().add(cg.view());
        isPlaying = false; elapsedTime = 0; simPeriod = 2; // 2 seconds
    }

    private GridPane simulationControllers() {
        var infoGrid = new GridPane();
        infoGrid.setHgap(35);
        infoGrid.setVgap(15);
        infoGrid.setPadding(new Insets(5,30,5,30));

        modelName = new Text("       Select Model");
        numSim = new Text("# of Simulation: 0");
        stepRate = new Text("Step Rate: " + 1/simPeriod + "/s");

        // add elements into the controlPanel
        var save = new Button("Save");
        var load = new Button("Load");
        var playStop = new Button("Play");
        playStop.setOnMouseClicked(e -> {
            isPlaying = !isPlaying;
            playStop.setText(isPlaying ? "Stop" : "Play");
            elapsedTime = 0;
        });

        var tick = new Button("Tick");
        tick.setOnMouseClicked(e -> graph.tick());
        var increase = new Button("Up");
        increase.setOnMouseClicked(e -> simPeriod = Math.max(0.1, simPeriod-0.1));
        var decrease = new Button("Down");
        decrease.setOnMouseClicked(e -> simPeriod = Math.min(5, simPeriod+0.1));

        chooseModel = new ComboBox<>();
        chooseModel.getItems().addAll(SIMULATION_MODELS);
        chooseModel.setValue(SIMULATION_MODELS[0]);

        infoGrid.add(save, 0, 0);
        infoGrid.add(load, 0, 1);
        infoGrid.add(playStop, 1, 0);
        infoGrid.add(tick, 1, 1);
        infoGrid.add(increase, 2, 0);
        infoGrid.add(decrease, 2, 1);
        infoGrid.add(numSim, 3, 0);
        infoGrid.add(stepRate, 3, 1);
        infoGrid.add(modelName, 4, 0);
        infoGrid.add(chooseModel, 4, 1);

        return infoGrid;
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
        stepRate.setText("Step Rate: " + ((double) Math.round(1/simPeriod * 100) / 100)  + "/s");
        numSim.setText("# of Simulation: "+graph.tickCount());
        handleModelChange();

        if(isPlaying) {
            elapsedTime += duration;
            if (elapsedTime >= simPeriod) {
                graph.tick();
                elapsedTime = 0;
            }
        }
    }

    public void handleModelChange() {
        if(graph.modelName().equals(chooseModel.getValue())) return;
        if(chooseModel.getValue().equals(GameOfLifeRule.MODEL_NAME)) {
            initializeSimulation(GameOfLife.generate()); // generate default
        } else if(chooseModel.getValue().equals(SegregationRule.MODEL_NAME)) {
            initializeSimulation(Segregation.generate());
        }
    }
}
