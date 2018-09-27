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
    public static final String LANGUAGE = "English";

    @Override
    public void start(Stage primaryStage) {
        GUI myGUI = new GUI(LANGUAGE);
        primaryStage.setResizable(false);
        myGUI.runGUI(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
