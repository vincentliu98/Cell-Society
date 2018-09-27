package visualization;

import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import simulation.Simulator;
import simulation.models.GameOfLifeModel;
import simulation.models.SegregationModel;
import simulation.models.SpreadingFireModel;
import simulation.models.WaTorModel;
import utility.ShapeUtils;

/**
 * SimulationControlPanel extends HBox and will be located in the bottom of UI
 * Within it, the user will be able to loading/saving settings from XML file,
 * start/pause the simulation, change the rate of simulation, or change the model
 *
 * @author Inchan Hwang
 * @author Vincent Liu
 */

public class SimulationControlPanel extends HBox {
    public static final String[] SIMULATION_MODELS = new String[] {
            GameOfLifeModel.MODEL_NAME,
            SegregationModel.MODEL_NAME,
            WaTorModel.MODEL_NAME,
            SpreadingFireModel.MODEL_NAME
    };


    private boolean isPlaying;
    private Text numTick, stepRate;
    private double simPeriod, elapsedTime;
    private ComboBox<String> chooseModel;
    private ComboBox<String> chooseShape;
    private Simulator<?> simulator;

    public SimulationControlPanel(
            EventHandler<? super MouseEvent> onLoad,
            EventHandler<? super MouseEvent> onSave,
            ChangeListener<String> onModelChange,
            ChangeListener<String> onShapeChange
    ) {
        super(25);
        getStyleClass().add("simControlPanelWrapper");

        var save = new Button("Save");
        save.setOnMouseClicked(onSave);
        var load = new Button("Load");
        load.setOnMouseClicked(onLoad);

        chooseModel = new ComboBox<>();
        chooseModel.getItems().addAll(SIMULATION_MODELS);
        chooseModel.setValue(SIMULATION_MODELS[0]);
        chooseShape.valueProperty().addListener(onShapeChange);

        chooseShape = new ComboBox<>();
        chooseShape.getItems().addAll(ShapeUtils.SHAPES);
        chooseShape.setValue(ShapeUtils.SHAPES[0]);

        chooseModel.valueProperty().addListener(onModelChange);
    }

    /**
     *
     * @param sim
     */
    public void setupPanel(
            Simulator<?> sim,
    ) {
        getChildren().clear();

        isPlaying = false;
        simulator = sim;
        elapsedTime = 0;
        simPeriod = 1;

        var grid = new GridPane();
        grid.getStyleClass().add("simControlPanel");

        numTick = new Text("# of ticks: 0");
        stepRate = new Text("Step Rate: " + 1/simPeriod + "/s");



        var playStop = new Button("Play");
        playStop.setOnMouseClicked(e -> {
            isPlaying = !isPlaying;
            playStop.setText(isPlaying ? "Stop" : "Play");
            elapsedTime = 0;
        });
        var tick = new Button("Tick");
        tick.setOnMouseClicked(e -> simulator.tick());

        var increase = new Button("Up");
        increase.setOnMouseClicked(e -> {
            simPeriod = Math.max(0.05, simPeriod-0.05);
            updateStepRate();
        });
        var decrease = new Button("Down");
        decrease.setOnMouseClicked(e -> {
            simPeriod = Math.min(5, simPeriod+0.05);
            updateStepRate();
        });


        grid.add(save, 0, 0);
        grid.add(load, 0, 1);
        grid.add(playStop, 1, 0);
        grid.add(tick, 1, 1);
        grid.add(increase, 2, 0);
        grid.add(decrease, 2, 1);
        grid.add(numTick, 3, 0);
        grid.add(stepRate, 3, 1);
        grid.add(chooseModel, 4, 0);
        grid.add(chooseShape, 4, 1);

        getChildren().add(grid);
    }

    /**
     * @param duration
     * @return
     */
    public boolean canTick(double duration) {
        if(isPlaying) {
            elapsedTime += duration;
            if (elapsedTime >= simPeriod) {
                elapsedTime = 0;
                return true;
            }
        } return false;
    }

    /**
     */
    public void updateStepRate() {
        stepRate.setText("Step Rate: " + ((double) Math.round(1/simPeriod * 100) / 100) + "/s");
    }

    /**
     * @param ticks
     */
    public void setNumTick(int ticks) { numTick.setText("# of ticks: "+ticks); }

    public String getChosenModel() { return chooseModel.getValue(); }
    public String getChosenShape() { return chooseShape.getValue(); }
}
