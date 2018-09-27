package visualization;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import simulation.Simulator;
import simulation.factory.GameOfLife;
import simulation.models.GameOfLifeModel;
import simulation.models.SegregationModel;
import simulation.models.SpreadingFireModel;
import simulation.models.WaTorModel;

import java.util.ResourceBundle;

/**
 * SimulationControlPanel extends HBox and will be located in the bottom of UI
 * Within it, the user will be able to loading/saving settings from XML file,
 * start/pause the simulation, change the rate of simulation, or change the model
 *
 * @author Inchan Hwang
 * @author Vincent Liu
 */

public class SimulationControlPanel extends HBox {
    public static final String[] SIMULATION_MODELS = new String[] {"", "", "", ""};

    private boolean isPlaying;
    private Text numTick, stepRate;
    private double simPeriod, elapsedTime;
    private ComboBox<String> chooseModel;
    private Simulator<?> simulator;
    private ResourceBundle myResources;

    public SimulationControlPanel(ResourceBundle myResources) {
        super(25);
        this.myResources = myResources;
        SIMULATION_MODELS[0] = myResources.getString("GameOfLifeModelName");
        SIMULATION_MODELS[1] = myResources.getString("SegregationModelName");
        SIMULATION_MODELS[2] = myResources.getString("WaTorModelName");
        SIMULATION_MODELS[3] = myResources.getString("SpreadingFireName");
        getStyleClass().add("simControlPanelWrapper");
    }

    /**
     *
     * @param sim
     * @param onLoad
     * @param onSave
     */
    public void setupPanel(
            Simulator<?> sim,
            EventHandler<? super MouseEvent> onLoad,
            EventHandler<? super MouseEvent> onSave
    ) {
        getChildren().clear();

        isPlaying = false;
        simulator = sim;
        elapsedTime = 0;
        simPeriod = 1;

        var grid = new GridPane();
        grid.getStyleClass().add("simControlPanel");

        var modelName = new Text(myResources.getString("SelectModel"));
        numTick = new Text(myResources.getString("DefaultNumTickDisplay"));
        stepRate = new Text(myResources.getString("DefaultStepRateDisplay") + 1/simPeriod + myResources.getString("StepRateUnit"));

        var save = new Button(myResources.getString("SaveButton"));
        save.setOnMouseClicked(onSave);
        var load = new Button(myResources.getString("LoadButton"));
        load.setOnMouseClicked(onLoad);


        var playStop = new Button(myResources.getString("PlayButton"));
        playStop.setOnMouseClicked(e -> {
            isPlaying = !isPlaying;
            playStop.setText(isPlaying ? myResources.getString("StopButton") : myResources.getString("PlayButton"));
            elapsedTime = 0;
        });
        var tick = new Button(myResources.getString("TickButton"));
        tick.setOnMouseClicked(e -> simulator.tick());

        var increase = new Button(myResources.getString("RateUpButton"));
        increase.setOnMouseClicked(e -> {
            simPeriod = Math.max(0.05, simPeriod-0.05);
            updateStepRate();
        });
        var decrease = new Button(myResources.getString("RateDownButton"));
        decrease.setOnMouseClicked(e -> {
            simPeriod = Math.min(5, simPeriod+0.05);
            updateStepRate();
        });

        chooseModel = new ComboBox<>();
        chooseModel.getItems().addAll(SIMULATION_MODELS);
        chooseModel.setValue(sim.modelName());

        grid.add(save, 0, 0);
        grid.add(load, 0, 1);
        grid.add(playStop, 1, 0);
        grid.add(tick, 1, 1);
        grid.add(increase, 2, 0);
        grid.add(decrease, 2, 1);
        grid.add(numTick, 3, 0);
        grid.add(stepRate, 3, 1);
        grid.add(modelName, 4, 0);
        grid.add(chooseModel, 4, 1);

        getChildren().add(grid);
    }

    /**
     *
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
     *
     */
    public void updateStepRate() {
        stepRate.setText(myResources.getString("DefaultStepRateDisplay") + ((double) Math.round(1/simPeriod * 100) / 100) + myResources.getString("StepRateUnit"));
    }

    /**
     *
     * @return
     */
    public String getChosenModel() { return chooseModel.getValue(); }

    /**
     *
     * @param ticks
     */
    public void setNumTick(int ticks) { numTick.setText(myResources.getString("NumTickDynamic")+ticks); }
}
