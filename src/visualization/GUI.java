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
import visualization.statistics.GameOfLifeStatistics;

import java.util.ResourceBundle;

/**
 * This is the Graphical User Interface for displaying the simulation models.
 * <p>There are four main panels:
 * <ul>
 *     <li>SimulationControl</li>
 *     <li>ModelChart</li>
 *     <li>ModelControl</li>
 *     <li>SimulationPanel</li>
 * </ul>
 * </p>
 * <p>SimulationControl manages the modelControl (i.e. update the model) and the modelChart (i.e. update the line chart),
 * and the modelControl manages simulationPanel (i.e. update the parameters for the existing model).</p>
 * <p>The controlPanel contains multiple buttons that will handle events, such as to load/save settings
 * from XML file, start/pause the simulation, change the rate of simulation, change the model, or change the cell shape</p>
 *
 * @author Vincent Liu
 * @author Inchan Hwang
 */
public class GUI {
    public static final int SCREEN_WIDTH = 840;
    public static final int SCREEN_HEIGHT = 726;

    public static final int COL_1_PERCENT = 25;
    public static final int COL_2_PERCENT = 75;
    public static final int ROW_1_PERCENT = 15;
    public static final int ROW_2_PERCENT = 70;
    public static final int ROW_3_PERCENT = 15;

    public static final int MODELCHART_ROW = 0;
    public static final int MODELCHART_COL = 0;
    public static final int MODELCHART_ROW_SPAN = 1;
    public static final int MODELCHART_COL_SPAN = 3;
    public static final int MODELCONTROL_ROW = 1;
    public static final int MODELCONTROL_COL = 0;
    public static final int SIMPANEL_ROW = 1;
    public static final int SIMPANEL_COL = 1;
    public static final int SIMCONTROL_ROW = 2;
    public static final int SIMCONTROL_COL = 0;
    public static final int SIMCONTROL_ROW_SPAN = 1;
    public static final int SIMCONTROL_COL_SPAN = 2;

    public static final int FRAMES_PER_SECOND = 10;
    public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    public static final double SECOND_DELAY = MILLISECOND_DELAY / 1000.;
    public static final String STYLESHEET = "style.css";

    private Window window;
    private GridPane root;
    private SimulationControl simControl;
    private ResourceBundle myResources;
    private String myLanguage;

    /**
     * Construct a GridPane containing its four components
     *
     * @param language
     */
    public GUI (String language) {
        myLanguage = language;
        myResources = ResourceBundle.getBundle(myLanguage);
        root = new GridPane();
        root.getStyleClass().add("root");

        var column1 = new ColumnConstraints();
        column1.setPercentWidth(COL_1_PERCENT);
        var column2 = new ColumnConstraints();
        column2.setPercentWidth(COL_2_PERCENT);
        root.getColumnConstraints().addAll(column1, column2);
        var row1 = new RowConstraints();
        row1.setPercentHeight(ROW_1_PERCENT);
        var row2 = new RowConstraints();
        row2.setPercentHeight(ROW_2_PERCENT);
        var row3 = new RowConstraints();
        row3.setPercentHeight(ROW_3_PERCENT);
        root.getRowConstraints().addAll(row1, row2, row3);

        simControl = new SimulationControl(window, myResources, myLanguage);

        root.add(new GameOfLifeStatistics(), MODELCHART_COL, MODELCHART_ROW, MODELCHART_COL_SPAN, MODELCHART_ROW_SPAN);
        root.add(simControl.getModelControl(), MODELCONTROL_COL, MODELCONTROL_ROW);
        root.add(simControl.getSimPanel(), SIMPANEL_ROW, SIMPANEL_COL);
        root.add(simControl, SIMCONTROL_COL, SIMCONTROL_ROW, SIMCONTROL_COL_SPAN, SIMCONTROL_ROW_SPAN);
    }

    /**
     * Runs the GUI by adding the stylesheet and starting the animation
     *
     * @param primaryStage
     */
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

    /**
     * Ticks the simulation control, while checking whether
     * it should update the view or not by checking simulation
     * control's status code
     *
     * @param duration
     */
    public void step(double duration) {
        if(simControl.consumeStatusCode() == StatusCode.UPDATE) {
            root.getChildren().clear();
            root.add(simControl.getModelChart(), MODELCHART_COL, MODELCHART_ROW, MODELCHART_COL_SPAN, MODELCHART_ROW_SPAN);
            root.add(simControl.getModelControl(), MODELCONTROL_COL, MODELCONTROL_ROW);
            root.add(simControl.getSimPanel(), SIMPANEL_ROW, SIMPANEL_COL);
            root.add(simControl, SIMCONTROL_COL, SIMCONTROL_ROW, SIMCONTROL_COL_SPAN, SIMCONTROL_ROW_SPAN);
        } simControl.tick(duration, simControl.getModelChart());
    }
}
