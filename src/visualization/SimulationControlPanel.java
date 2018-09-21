package visualization;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import simulation.Simulator;
import xml.XMLParser;

import java.io.File;

import static visualization.GUI.SIMULATION_MODELS;

public class SimulationControlPanel extends HBox {
    private boolean isPlaying, isFileLoaded;
    private Text numTick, stepRate;
    private double simPeriod, elapsedTime;
    private ComboBox<String> chooseModel;
    private Simulator<?> simulator;

    public SimulationControlPanel(Simulator<?> sim) {
        super(25);
        isPlaying = isFileLoaded = false;

        simulator = sim;

        var grid = new GridPane();
        grid.setHgap(35);
        grid.setVgap(15);
        grid.setPadding(new Insets(5,30,5,30));

        var modelName = new Text("       Select Model");
        numTick = new Text("# of ticks: 0");
        stepRate = new Text("Step Rate: " + 1/simPeriod + "/s");

        // add elements into the controlPanel
        var save = new Button("Save");

        var load = new Button("Load");
        load.setOnMouseClicked(e -> handleFileLoad());
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
            simPeriod = Math.max(0.1, simPeriod-0.1);
            updateStepRate();
        });

        var decrease = new Button("Down");
        decrease.setOnMouseClicked(e -> {
            simPeriod = Math.min(5, simPeriod+0.1);
            updateStepRate();
        });

        chooseModel = new ComboBox<>();
        chooseModel.getItems().addAll(SIMULATION_MODELS);
        chooseModel.setValue(SIMULATION_MODELS[0]);

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

    private void handleFileLoad() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(null);
        if(file == null) return;
        var parser = new XMLParser();
        parser.getSimulationModel(file);
    }

    public boolean canTick(double duration) {
        if(isPlaying) {
            elapsedTime += duration;
            if (elapsedTime >= simPeriod) {
                elapsedTime = 0;
                return true;
            }
        } return false;
    }

    public void updateStepRate() {
        stepRate.setText("Step Rate: " + ((double) Math.round(1/simPeriod * 100) / 100) + "/s");
    }
    public String getChosenModel() { return chooseModel.getValue(); }
    public void setNumTick(int ticks) { numTick.setText("# of ticks: "+ticks); }
    public void reset(Simulator<?> sim) {
        isPlaying = false; elapsedTime = 0; simPeriod = 1;
        simulator = sim;
    }
}
