package visualization;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import simulation.models.GameOfLifeModel;
import simulation.models.SegregationModel;
import simulation.models.SpreadingFireModel;
import simulation.models.WaTorModel;
import utility.ShapeUtils;
import visualization.model_controls.*;
import xml.XMLException;
import xml.parser.*;

import java.io.File;
import java.util.ResourceBundle;

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
    private ResourceBundle myResources;
    private String myLanguage;

    private StatusCode statusCode;
    private boolean isPlaying;
    private Text numTick, stepRate;
    private Button save, load, tick, playStop, inc, dec;
    private double simPeriod, elapsedTime;
    private ComboBox<String> chooseModel;
    private ComboBox<String> chooseShape;

    private ModelControl<?> modelControl;

    public SimulationControl(Window window_, ResourceBundle myResources, String myLanguage) {
        super(25);
        window = window_;
        statusCode = StatusCode.NO_UPDATE;
        isPlaying = false;
        elapsedTime = 0;
        simPeriod = 1;
        this.myResources = myResources;
        this.myLanguage = myLanguage;
        SIMULATION_MODELS[0] = myResources.getString("GameOfLifeModelName");
        SIMULATION_MODELS[1] = myResources.getString("SegregationModelName");
        SIMULATION_MODELS[2] = myResources.getString("WaTorModelName");
        SIMULATION_MODELS[3] = myResources.getString("SpreadingFireName");

        getStyleClass().add("simControlPanelWrapper");
        initializeModelControl(SIMULATION_MODELS[0], ShapeUtils.SHAPES[0]);
        initializeStructure();
        initializeFunctionality();
    }


    /**
     *  Initializes the components, without any functionality
     */
    private void initializeStructure() {
        getStyleClass().add("simControlPanelWrapper");
        var grid = new GridPane();
        grid.getStyleClass().add("simControlPanel");
        save = new Button(myResources.getString("SaveButton"));
        load = new Button(myResources.getString("LoadButton"));
        playStop = new Button(myResources.getString("PlayButton"));
        numTick = new Text(myResources.getString("DefaultNumTickDisplay"));
        stepRate = new Text(myResources.getString("DefaultStepRateDisplay") +
                1/simPeriod + myResources.getString("StepRateUnit"));
        tick = new Button(myResources.getString("TickButton"));
        inc = new Button(myResources.getString("RateUpButton"));
        dec = new Button(myResources.getString("RateDownButton"));
        chooseModel = new ComboBox<>();
        chooseModel.getItems().addAll(SIMULATION_MODELS);
        chooseModel.setValue(SIMULATION_MODELS[0]);
        chooseShape = new ComboBox<>();
        chooseShape.getItems().addAll(ShapeUtils.SHAPES);
        chooseShape.setValue(ShapeUtils.SHAPES[0]);
        var modelName = new Text(myResources.getString("SelectModel"));
        var shapeName = new Text(myResources.getString("SelectShape"));
        var comboBox = new GridPane();
        comboBox.add(modelName, 0,0);
        comboBox.add(shapeName, 0,1);
        comboBox.add(chooseModel, 1, 0);
        comboBox.add(chooseShape, 1, 1);
        comboBox.getStyleClass().add("combo-choice");

        grid.add(save, 0, 0);
        grid.add(load, 0, 1);
        grid.add(playStop, 1, 0);
        grid.add(tick, 1, 1);
        grid.add(inc, 2, 0);
        grid.add(dec, 2, 1);
        grid.add(numTick, 3, 0);
        grid.add(stepRate, 3, 1);
        grid.add(comboBox, 4, 0, 1, 2);

        getChildren().add(grid);
    }

    /**
     *  Sets up each component's interactions
     */
    private void initializeFunctionality() {
        save.setOnMouseClicked(e -> handleFileSave());
        load.setOnMouseClicked(e -> handleFileLoad());
        playStop.setOnMouseClicked(e -> handlePlayStop());
        tick.setOnMouseClicked(e -> modelControl.simulator().tick());
        inc.setOnMouseClicked(e -> handleSpeedChange(-0.05));
        dec.setOnMouseClicked(e -> handleSpeedChange(0.05));
        chooseModel.valueProperty().addListener((a, b, c) -> handleModelChange(c));
        chooseShape.valueProperty().addListener((a, b, c) -> handleShapeChange(c));
    }

    /**
     * It first checks whether it should update the model control,
     * reporting to the GUI via statusCode. <br>
     *  It keeps a internal timer to tick once in simPeriod.
     * @param duration
     */
    public void tick(double duration) {
        if(modelControl.consumeIsDirty()) {
            stop();
            statusCode = StatusCode.UPDATE;
        }

        if(isPlaying) {
            elapsedTime += duration;
            if (elapsedTime >= simPeriod) {
                elapsedTime = 0;
                modelControl.simulator().tick();
                numTick.setText(myResources.getString("NumTickDynamic")+modelControl.simulator().tickCount());
            }
        }
    }

    private void handlePlayStop() {
        isPlaying = !isPlaying;
        playStop.setText(isPlaying ? "Stop" : "Play");
        elapsedTime = 0;
    }

    private void stop() {
        isPlaying = true;
        handlePlayStop();
    }

    private void handleSpeedChange(double by) {
        simPeriod = Math.min(Math.max(0.1, simPeriod+by), 20);
        stepRate.setText(myResources.getString("DefaultStepRateDisplay") +
                ((double) Math.round(1/simPeriod * 100) / 100) + myResources.getString("StepRateUnit"));
    }

    private void handleModelChange(String newModel) {
        stop();
        initializeModelControl(newModel, chooseShape.getValue());
    }

    private void handleShapeChange(String newShape) {
        stop();
        initializeModelControl(chooseModel.getValue(), newShape);
    }

    private void handleFileLoad() {
        try {
            stop();
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle(myResources.getString("OpenFile"));
            File file = fileChooser.showOpenDialog(window);
            var modelName = ParentXMLParser.peekModelName(file);
            initializeModelControl(modelName, file);
        } catch (XMLException e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText(e.getMessage());
            errorAlert.setContentText(e.getMessage());
            errorAlert.showAndWait();
        }
    }

    private void handleFileSave() {
        stop();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(myResources.getString("SaveFile"));
        File file = fileChooser.showSaveDialog(window);
        if(file == null) return; // display "OH NO!" DIALOG
        modelControl.simulator().getWriter(file).generate();
    }

    /**
     *  There are two ways of initializing a simulation - from file or from factory.
     *  This one handles the former
     * @param modelName
     * @param file
     */

    private void initializeModelControl(String modelName, File file) {
        if(modelName.equals(GameOfLifeModel.MODEL_NAME)) {
            modelControl = new GameOfLifeControl(new GameOfLifeXMLParser(myLanguage).getSimulator(file));
        } else if(modelName.equals(SegregationModel.MODEL_NAME)) {
            modelControl = new SegregationControl(new SegregationXMLParser(myLanguage).getSimulator(file));
        } else if(modelName.equals(SpreadingFireModel.MODEL_NAME)) {
            modelControl = new SpreadingFireControl(new SpreadingFireXMLParser(myLanguage).getSimulator(file));
        } else if(modelName.equals(WaTorModel.MODEL_NAME)) {
            modelControl = new WaTorControl(new WaTorXMLParser(myLanguage).getSimulator(file));
        }

        statusCode = StatusCode.UPDATE;
    }

    /**
     *  There are two ways of initializing a simulation - from file or from factory.
     *  This one handles the latter
     * @param modelName
     * @param shape
     */
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

        statusCode = StatusCode.UPDATE;
    }

    SimulationPanel getSimPanel() { return modelControl.simPanel(); }
    ModelControl<?> getModelControl() { return modelControl; }

    /**
     * It's named "consume" since once drawn, statusCode should go back to its default state.
     * @return
     */
    public StatusCode consumeStatusCode() {
        var ret = statusCode;
        statusCode = StatusCode.NO_UPDATE;
        return ret;
    }
}
