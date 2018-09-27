package visualization;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import simulation.models.GameOfLifeModel;
import simulation.models.SegregationModel;
import simulation.models.SpreadingFireModel;
import simulation.models.WaTorModel;
import utility.ShapeUtils;
import visualization.model_panels.*;
import xml.parser.*;

import java.io.File;

/**
 * SimulationControlPanel extends HBox and will be located in the bottom of UI
 * Within it, the user will be able to loading/saving settings from XML file,
 * start/pause the simulation, change the rate of simulation, or change the model
 *
 * @author Inchan Hwang
 * @author Vincent Liu
 */

public class SimulationControl extends HBox {
    public static final String[] SIMULATION_MODELS = new String[] {
            GameOfLifeModel.MODEL_NAME,
            SegregationModel.MODEL_NAME,
            WaTorModel.MODEL_NAME,
            SpreadingFireModel.MODEL_NAME
    };

    private Window window; // for load/save binding

    private boolean isPlaying;
    private Text numTick, stepRate;
    private Button save, load, tick, playStop, inc, dec;
    private double simPeriod, elapsedTime;
    private ComboBox<String> chooseModel;
    private ComboBox<String> chooseShape;

    private ModelControl<?> modelControl;
    private VBox simPanel;

    public SimulationControl(Window window_) {
        super(25);
        window = window_;
        isPlaying = false;
        elapsedTime = 0;
        simPeriod = 1;
        simPanel = new VBox();
        simPanel.getStyleClass().add("simPanel");
        initializeModelControl(SIMULATION_MODELS[0], ShapeUtils.SHAPES[0]);
        initializeStructure();
        initializeFunctionality();
    }

    private void initializeStructure() {
        getStyleClass().add("simControlPanelWrapper");
        var grid = new GridPane();
        grid.getStyleClass().add("simControlPanel");
        save = new Button("Save");
        load = new Button("Load");
        playStop = new Button("Play");
        numTick = new Text("# of ticks: 0");
        stepRate = new Text("Step Rate: " + 1/simPeriod + "/s");
        tick = new Button("Tick");
        inc = new Button("Up");
        dec = new Button("Down");
        chooseModel = new ComboBox<>();
        chooseModel.getItems().addAll(SIMULATION_MODELS);
        chooseModel.setValue(SIMULATION_MODELS[0]);
        chooseShape = new ComboBox<>();
        chooseShape.getItems().addAll(ShapeUtils.SHAPES);
        chooseShape.setValue(ShapeUtils.SHAPES[0]);
        grid.add(save, 0, 0);
        grid.add(load, 0, 1);
        grid.add(playStop, 1, 0);
        grid.add(tick, 1, 1);
        grid.add(inc, 2, 0);
        grid.add(dec, 2, 1);
        grid.add(numTick, 3, 0);
        grid.add(stepRate, 3, 1);
        grid.add(chooseModel, 4, 0);
        grid.add(chooseShape, 4, 1);
        getChildren().add(grid);
    }

    private void initializeFunctionality() {
        save.setOnMouseClicked(e -> handleFileSave());
        load.setOnMouseClicked(e -> handleFileLoad());
        playStop.setOnMouseClicked(e -> handlePlayStop());
        tick.setOnMouseClicked(e -> modelControl.simulator().tick());
        inc.setOnMouseClicked(e -> handleSpeedChange(-0.05));
        dec.setOnMouseClicked(e -> handleSpeedChange(0.05));
        chooseModel.valueProperty().addListener((a, b, c) -> handleShapeChange(c));
        chooseShape.valueProperty().addListener((a, b, c) -> handleModelChange(c));
    }

    public void tick(double duration) {
        if(isPlaying) {
            elapsedTime += duration;
            if (elapsedTime >= simPeriod) {
                elapsedTime = 0;
                modelControl.simulator().tick();
                numTick.setText("# of ticks: "+modelControl.simulator().tickCount());
            }
        }
    }

    private void handlePlayStop() {
        isPlaying = !isPlaying;
        playStop.setText(isPlaying ? "Stop" : "Play");
        elapsedTime = 0;
    }

    private void handleSpeedChange(double by) {
        simPeriod = Math.min(Math.min(5, simPeriod+by), 20);
        stepRate.setText("Step Rate: " + ((double) Math.round(1/simPeriod * 100) / 100) + "/s");
    }

    private void handleModelChange(String newModel) {
        initializeModelControl(newModel, chooseShape.getValue());
    }

    private void handleShapeChange(String newShape) {
        initializeModelControl(chooseModel.getValue(), newShape);
    }

    private void handleFileLoad() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(window);
        if(!file.exists()) return;
        var modelName = ParentXMLParser.peekModelName(file);
        initializeModelControl(modelName, file);
    }

    private void handleFileSave() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showSaveDialog(window);
        if(file == null) return; // display "OH NO!" DIALOG
        modelControl.simulator().getWriter(file).generate();
    }

    // There are two ways of initializing a simulation - from file or from factory

    private void initializeModelControl(String modelName, File file) {
        if(modelName.equals(GameOfLifeModel.MODEL_NAME)) {
            modelControl = new GameOfLifeControl(new GameOfLifeXMLParser().getSimulator(file));
        } else if(modelName.equals(SegregationModel.MODEL_NAME)) {
            modelControl = new SegregationControl(new SegregationXMLParser().getSimulator(file));
        } else if(modelName.equals(SpreadingFireModel.MODEL_NAME)) {
            modelControl = new SpreadingFireControl(new SpreadingFireXMLParser().getSimulator(file));
        } else if(modelName.equals(WaTorModel.MODEL_NAME)) {
            modelControl = new WaTorControl(new WaTorXMLParser().getSimulator(file));
        }
    }

    private void initializeModelControl(String modelName, String shape) {
        if(modelName.equals(GameOfLifeModel.MODEL_NAME)) {
            modelControl = new GameOfLifeControl(shape);
        } else if(modelName.equals(SegregationModel.MODEL_NAME)) {
            modelControl = new SegregationControl(shape);
        } else if(modelName.equals(SpreadingFireModel.MODEL_NAME)) {
            modelControl =  new SpreadingFireControl(shape);
        } else if(modelName.equals(WaTorModel.MODEL_NAME)) {
            modelControl = new WaTorControl(shape);
        }
    }

    public VBox getSimPanel() { return simPanel; }
    public ModelControl<?> getModelControl() { return modelControl; }
}
