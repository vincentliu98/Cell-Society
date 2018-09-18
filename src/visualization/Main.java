package visualization;

import javafx.application.Application;
import javafx.stage.Stage;
import simulation.CellGraph;
import simulation.factory.GameOfLife;

/**
 * This is the main class for the GUI to run
 *
 * @author Vincent Liu
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        GUI myGUI = new GUI();
        myGUI.runGUI(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
