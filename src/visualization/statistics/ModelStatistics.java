package visualization.statistics;

import javafx.scene.chart.*;

import java.util.Map;
import java.util.ResourceBundle;

/**
 * An abstract class that serves as a building block for customized line charts
 * Contains x and y axis of the time and the number of each elements from the model.
 * The line chart dynamically expand when the new data feeds in
 *
 * @author Vincent Liu
 */

public abstract class ModelStatistics extends LineChart{
    private NumberAxis xAxis;
    private final NumberAxis yAxis;

    /**
     * Constructor: Add the axis and change some settings
     */
    ModelStatistics() {
        super(new NumberAxis(), new NumberAxis());
        xAxis = (NumberAxis) getXAxis();
        xAxis.setLabel("Time (0.1s)");
        xAxis.setMinorTickVisible(false);
        xAxis.setAutoRanging(true);

        yAxis = (NumberAxis) getYAxis();
        yAxis.setLabel("Number");
        yAxis.setAutoRanging(true);

        setAnimated(true);
        autosize();
        setCreateSymbols(false);
        getStyleClass().add("line-chart");
    }

    /**
     * Allow the specific objects from each model to update themselves
     *
     * @param durationCounter
     * @param myResources
     * @param newStatistics
     */
    public abstract void updateStatistics(Double durationCounter, ResourceBundle myResources, Map<String, Integer> newStatistics);
}
