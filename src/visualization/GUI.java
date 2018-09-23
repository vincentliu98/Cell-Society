package visualization;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
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
import visualization.model_panels.*;
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

    public static final String[] SIMULATION_MODELS = new String[] {
            GameOfLifeModel.MODEL_NAME,
            SegregationModel.MODEL_NAME,
            WaTorModel.MODEL_NAME,
            SpreadingFireModel.MODEL_NAME
    };

    private Window window;
    private GridPane root;
    private SimulationControlPanel simControlPanel;
    private ModelPanel modelPanel;
    private GameOfLifePanel gameOfLifePanel;
    private SegregationPanel segregationPanel;
    private SpreadingFirePanel spreadingFirePanel;
    private WaTorPanel waTorPanel;
    private VBox simPanel;
    private String modelName;
    private boolean paramChanged;
    private int cellNum;

    private double segregationThreshold;

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

        modelPanel = new GameOfLifePanel();
        modelPanel.getStyleClass().add("modelPanel");

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
        System.out.println("trying to create new model");
        simulator = sim;
        simControlPanel.setupPanel(simulator, e -> handleFileLoad(), e -> handleFileSave());
        simPanel.getChildren().clear();
        simPanel.getChildren().add(simulator.view());
        System.out.println("new model should be added");
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
        handleModelPanelChange();
        handleModelChange();
        handleModelPanelParametersChange();
        handleCellNumChange();
    }

    public void handleModelPanelChange() {
        modelName = simControlPanel.getChosenModel();
        if (modelName.equals(simulator.modelName())) return;
        generateModelPanelByName();
        paramChanged=!paramChanged;
    }

    public void handleModelPanelParametersChange() {
        if(modelName.equals(GameOfLifeModel.MODEL_NAME)) {

        } else if(modelName.equals(SegregationModel.MODEL_NAME)) {
            segregationThreshold = segregationPanel.getThresholdVal();
            if (segregationPanel.getChangeThreshold()) {
                initializeSimulation(Segregation.generate(cellNum, segregationThreshold));
                System.out.println("New model created");
                segregationPanel.setChangeThreshold(false);
            }
        } else if(modelName.equals(SpreadingFireModel.MODEL_NAME)) {

        } else if(modelName.equals(WaTorModel.MODEL_NAME)) {

        }
    }

    public void handleModelChange() {
        if (simulator.modelName().equals(modelName)) return;
        generateModelByName(DEFAULT_CELL_NUM);
    }

    public void handleCellNumChange() {
        cellNum = modelPanel.getCellNum();
        if (modelPanel.getChangeCellNum()) {
            generateModelByName(cellNum);
        }
        modelPanel.setChangeCellNum(false);
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

    public void generateModelByName(int cellNum) {
        if(modelName.equals(GameOfLifeModel.MODEL_NAME)) {
            initializeSimulation(GameOfLife.generate(cellNum));
        } else if(modelName.equals(SegregationModel.MODEL_NAME)) {
            initializeSimulation(Segregation.generate(cellNum, segregationThreshold));
        } else if(modelName.equals(SpreadingFireModel.MODEL_NAME)) {
            initializeSimulation(SpreadingFire.generate(cellNum));
        } else if(modelName.equals(WaTorModel.MODEL_NAME)) {
            initializeSimulation(WaTor.generate(cellNum));
        }
    }

    public void generateModelPanelByName() {
        if(modelName.equals(GameOfLifeModel.MODEL_NAME)) {
            gameOfLifePanel = new GameOfLifePanel();
            gameOfLifePanel.getStyleClass().add("modelPanel");
            modelPanel.getChildren().clear();
            modelPanel.getChildren().add(gameOfLifePanel);
        } else if(modelName.equals(SegregationModel.MODEL_NAME)) {
            segregationPanel = new SegregationPanel();
            segregationPanel.getStyleClass().add("modelPanel");
            modelPanel.getChildren().clear();
            modelPanel.getChildren().add(segregationPanel);
        } else if(modelName.equals(SpreadingFireModel.MODEL_NAME)) {
            spreadingFirePanel = new SpreadingFirePanel();
            spreadingFirePanel.getStyleClass().add("modelPanel");
            modelPanel.getChildren().clear();
            modelPanel.getChildren().add(spreadingFirePanel);
        } else if(modelName.equals(WaTorModel.MODEL_NAME)) {
            waTorPanel = new WaTorPanel();
            waTorPanel.getStyleClass().add("modelPanel");
            modelPanel.getChildren().clear();
            modelPanel.getChildren().add(waTorPanel);
        }
    }
}
