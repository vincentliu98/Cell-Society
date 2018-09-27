package visualization;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * The Main class initializes the GUI and run the GUI
 *
 * @author Vincent Liu
 * @author Inchan Hwang
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        GUI myGUI = new GUI("English");
        primaryStage.setResizable(false);
        myGUI.runGUI(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
