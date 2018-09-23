package visualization;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;
import simulation.Simulator;
import simulation.factory.GameOfLife;
import simulation.factory.Segregation;
import simulation.factory.SpreadingFire;
import simulation.factory.WaTor;
import simulation.models.GameOfLifeModel;
import simulation.models.SegregationModel;
import simulation.models.SpreadingFireModel;
import simulation.models.WaTorModel;
import xml.parser.ParentXMLParser;

import java.io.File;

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
 * @author Inchan Hwang
 */
public class GUI {
    public static final int SCREEN_WIDTH = 700;
    public static final int SCREEN_HEIGHT = 550;
    public static final int DEFAULT_CELL_NUM = 10;

    public static final int FRAMES_PER_SECOND = 10;
    public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    public static final double SECOND_DELAY = MILLISECOND_DELAY / 1000.;

    private Window window;
    private GridPane root;
    private SimulationControlPanel simControlPanel;
    private ModelPanel modelPanel;
    private VBox simPanel;
    private String modelName;

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

        modelPanel = new ModelPanel();
        simControlPanel = new SimulationControlPanel();
        simPanel = new VBox();
        simPanel.getStyleClass().add("simPanel");

        // add the three major layouts
        root.add(modelPanel, 0, 0);
        root.add(simPanel, 1, 0);
        root.add(simControlPanel, 0, 1, 2, 1);

        initializeSimulation(GameOfLife.generate(DEFAULT_CELL_NUM));
    }

    protected void initializeSimulation(Simulator<?> sim) {
        simulator = sim;
        simControlPanel.setupPanel(simulator, e -> handleFileLoad(), e -> handleFileSave());
        simPanel.getChildren().clear();
        simPanel.getChildren().add(simulator.view());
    }

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

    public void step(double duration) {
        if(simControlPanel.canTick(duration)) simulator.tick();
        simControlPanel.setNumTick(simulator.tickCount());
        simControlPanel.updateStepRate();
        handleModelChange(); // by choose model
        handleCellNumChange();
    }

    public void handleModelChange() {
        modelName = simControlPanel.getChosenModel();
        if(simulator.modelName().equals(modelName)) return;
        generateModelByName(modelPanel.getCellNum());
    }

    public void handleCellNumChange() {
        if (modelPanel.getChangeCellNum()) {
            var cellNum = modelPanel.getCellNum();
            generateModelByName(cellNum);
        }
        modelPanel.setChangeCellNum(false);
    }

    public void generateModelByName(int cellNum) {
        if(modelName.equals(GameOfLifeModel.MODEL_NAME)) {
            initializeSimulation(GameOfLife.generate(cellNum));
        } else if(modelName.equals(SegregationModel.MODEL_NAME)) {
            initializeSimulation(Segregation.generate(cellNum));
        } else if(modelName.equals(SpreadingFireModel.MODEL_NAME)) {
            initializeSimulation(SpreadingFire.generate(cellNum));
        } else if(modelName.equals(WaTorModel.MODEL_NAME)) {
            initializeSimulation(WaTor.generate(cellNum));
        }
    }

    private void handleFileLoad() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(window);
        if(!file.exists()) return;
        initializeSimulation(new ParentXMLParser().getSimulator(file));
    }

    private void handleFileSave() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showSaveDialog(window);
        if(file == null) return; // display "OH NO!" DIALOG
        simulator.getWriter(file).generate();
    }
}
