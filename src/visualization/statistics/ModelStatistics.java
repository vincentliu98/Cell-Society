package visualization.statistics;

import javafx.scene.chart.*;

import java.util.Map;
import java.util.ResourceBundle;

/**
 * A 2D chart displaying the time and the number of each elements from the model.
 * Whenever the lines reach the right side of the chart, the scale decreases to half to accommodate all the data
 *
 * @author Vincent Liu
 */

public abstract class ModelStatistics extends LineChart{
    private NumberAxis xAxis;
    private final NumberAxis yAxis;

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

    public abstract void updateStatistics(Double durationCounter, ResourceBundle myResources, Map<String, Integer> newStatistics);
}
