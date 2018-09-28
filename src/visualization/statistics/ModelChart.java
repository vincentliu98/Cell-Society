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

public abstract class ModelChart extends LineChart{
    private LineChart<Number, Number> lineChart;
    private NumberAxis xAxis;
    private final NumberAxis yAxis = new NumberAxis();

    ModelChart() {
        super(new NumberAxis(), new NumberAxis());
        xAxis = (NumberAxis) getXAxis();
        xAxis.setLabel("Time (0.1s)");
        yAxis.setLabel("Number");
        xAxis.setMinorTickVisible(false);
        xAxis.setAutoRanging(true);

        lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setAnimated(true);
        lineChart.autosize();
        lineChart.setCreateSymbols(false);
        lineChart.getStyleClass().add("line-chart");
    }

    public LineChart<Number, Number> getLineChart() {
        return lineChart;
    }

    public abstract void updateStatistics(Double durationCounter, ResourceBundle myResources, Map<String, Integer> newStatistics);

//    public Pane getChartBox() {
//        HBox layout = new HBox();
//        layout.getChildren().add(lineChart);
//        layout.getStyleClass().add("chartBox");
//        layout.setAlignment(Pos.CENTER);
//        return layout;
//    }
}
