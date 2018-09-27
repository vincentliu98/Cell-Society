package visualization;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;

import java.util.ResourceBundle;

/**
 * This is the Graphical User Interface for displaying the simulation models.
 * It contains three components:
 *  1. modelPanel that displays the model's parameters
 *  2. simPanel that displays the real-time simulation
 *  3. controlPanel that allows the user to adjust settings
 *
 * The controlPanel contains multiple buttons that will handle events, such as loading/saving settings
 * from XML file, start/pause the simulation, change the rate of simulation, or change the model
 *
 * @author Vincent Liu
 * @author Inchan Hwang
 */
public class GUI {
    public static final int SCREEN_WIDTH = 840;
    public static final int SCREEN_HEIGHT = 726;

    public static final int FRAMES_PER_SECOND = 10;
    public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    public static final double SECOND_DELAY = MILLISECOND_DELAY / 1000.;
    public static final String STYLESHEET = "style.css";

    private Window window;
    private GridPane root;
    private SimulationControl simControl;
    private ResourceBundle myResources;
    private String myLanguage;

    public GUI (String language) {
        myLanguage = language;
        myResources = ResourceBundle.getBundle(myLanguage);
        root = new GridPane();
        root.getStyleClass().add("root");

        var column1 = new ColumnConstraints();
        column1.setPercentWidth(20);
        var column2 = new ColumnConstraints();
        column2.setPercentWidth(80);
        root.getColumnConstraints().addAll(column1, column2);
        var row1 = new RowConstraints();
        row1.setPercentHeight(85);
        var row2 = new RowConstraints();
        row2.setPercentHeight(15);
        root.getRowConstraints().addAll(row1, row2);

        simControl = new SimulationControl(window, myResources, myLanguage);

        root.add(simControl.getModelControl(), 0, 0);
        root.add(simControl.getSimPanel(), 1, 0);
        root.add(simControl, 0, 1, 2, 1);
    }

    public void runGUI (Stage primaryStage) {
        window = primaryStage;

        primaryStage.setTitle(myResources.getString("PrimaryStageTitle"));
        Scene scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT);
        scene.getStylesheets().add(STYLESHEET);
        primaryStage.setScene(scene);
        primaryStage.show();

        var frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));
        var animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
    }

    public void step(double duration) {
        if(simControl.consumeStatusCode() == StatusCode.UPDATE) {
            root.getChildren().clear();
            root.add(simControl.getModelControl(), 0, 0);
            root.add(simControl.getSimPanel(), 1, 0);
            root.add(simControl, 0, 1, 2, 1);
        } simControl.tick(duration);
    }
}
