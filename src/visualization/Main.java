package visualization;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * This is the main class for the GUI to run
 *
 * @author Vincent Liu
 * @author Inchan Hwang
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        GUI myGUI = new GUI();
        primaryStage.setResizable(false);
        myGUI.runGUI(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
