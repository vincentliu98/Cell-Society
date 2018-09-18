package visualization;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {
    public static final int SCREEN_WIDTH = 700;
    public static final int SCREEN_HEIGHT = 550;

    @Override
    public void start(Stage primaryStage) throws Exception{
        GridPane root = new GridPane();

        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(20);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(80);
        root.getColumnConstraints().addAll(column1, column2);
        RowConstraints row1 = new RowConstraints();
        row1.setPercentHeight(85);
        RowConstraints row2 = new RowConstraints();
        row2.setPercentHeight(15);
        root.getRowConstraints().addAll(row1, row2);

        root.setPadding(new Insets(15,15,15,15));
        root.setVgap(10);
        root.setHgap(10);
        // add three major layouts
        Text model_panel_text = new Text("Model Panel");
        VBox modelPanel = new VBox();
        modelPanel.setStyle("-fx-border-color: black;\n");
        modelPanel.getChildren().add(model_panel_text);
        modelPanel.setSpacing(25);

        Text simulation_text = new Text("Simulation");
        VBox simulationPanel = new VBox();
        simulationPanel.setStyle("-fx-border-color: black;\n");
        simulationPanel.getChildren().add(simulation_text);
        simulationPanel.setSpacing(25);

        HBox controlPanel = new HBox();
        controlPanel.setStyle("-fx-border-color: black;\n");
        controlPanel.setSpacing(25);

        // add elements into the controlPanel
        Button save = new Button("Save");
        //        save.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e)->{// do something});
        Button load = new Button("Load");
        //        load.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e)->{// do something});
        Button stop = new Button("Stop");
        //        stop.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e)->{// do something});
        Button begin = new Button("Begin");
        //        begin.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e)->{// do something});
        Button increase = new Button("Up");
        //        increase.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e)->{// do something});
        Button decrease = new Button("Down");
        //        decrease.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e)->{// do something});

        ComboBox chooseModel = new ComboBox();
        chooseModel.getItems().addAll(
                "Game of Life",
                "Segregation",
                "Wa-Tor",
                "Spreading Fire"
        );
        Text modelName = new Text("Select Model");
        Text numSim = new Text("# of Simulation: XXX");
        Text stepRate = new Text("Step Rate: XXX");

        GridPane infoGrid = new GridPane();
        infoGrid.setHgap(35);
        infoGrid.setVgap(15);
        infoGrid.setPadding(new Insets(5,30,5,30));

        infoGrid.add(save, 0, 0);
        infoGrid.add(load, 0, 1);
        infoGrid.add(stop, 1, 0);
        infoGrid.add(begin, 1, 1);
        infoGrid.add(increase, 2, 0);
        infoGrid.add(decrease, 2, 1);
        infoGrid.add(numSim, 3, 0);
        infoGrid.add(stepRate, 3, 1);
        infoGrid.add(modelName, 4, 0);
        infoGrid.add(chooseModel, 4, 1);

        controlPanel.getChildren().add(infoGrid);
        // add the three major layouts
        root.add(modelPanel, 0, 0);
        root.add(simulationPanel, 1, 0);
        root.add(controlPanel, 0, 1, 2, 1);
        // display the root
        primaryStage.setTitle("Simulations");
        Scene scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT);
        scene.getStylesheets().add("visualization/style.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
