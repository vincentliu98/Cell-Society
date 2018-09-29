package visualization.statistics;

import javafx.scene.chart.*;

import java.util.Map;
import java.util.ResourceBundle;

/**
 * An abstract class that serves as a building block for customized line charts. <br>
 * <ul>
 *     <li>x Axis: time (0.1s)</li>
 *     <li>y Axis: Number of Cells</li>
 * </ul>
 * The line chart dynamically expand when the new data feeds in.
 * <br>
 * This class is made abstract because the line chart specific to each model need to update their parameters,
 * while their parameters differ in names and size. Therefore, I made the updateStatistics method abstract.
 * The commonality between the different line charts are they share the same axes, thus the constructor of the
 * ModelStatistics can achieve that.
 *
 * @author Vincent Liu
 */

public abstract class ModelStatistics extends LineChart{
    private NumberAxis xAxis;
    private final NumberAxis yAxis;

    /**
     * Adds the two axes and change settings for the chart and the axes
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
     * An abstract method that allows the specific objects from each model to update themselves
     *
     * @param durationCounter
     * @param myResources
     * @param newStatistics
     */
    public abstract void updateStatistics(Double durationCounter, ResourceBundle myResources, Map<String, Integer> newStatistics);
}
