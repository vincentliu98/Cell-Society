package visualization;

import javafx.collections.*;
import javafx.geometry.Pos;
import javafx.scene.chart.*;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;

import java.util.*;

/**
 * A 2D chart displaying the time and the number of each elements from the model.
 * Whenever the lines reach the right side of the chart, the scale decreases to half to accommodate all the data
 *
 * @author Vincent Liu
 */

public class ModelChart{
    private LineChart<Number, Number> lineChart;
    private ObservableList<Integer> number;
    private HBox layout;
    private NumberAxis xAxis = new NumberAxis();
    private NumberAxis yAxis = new NumberAxis();

    public ModelChart() {
        xAxis.setLabel("Time (s)");
        yAxis.setLabel("Number");
        xAxis.setMinorTickVisible(false);
        xAxis.setAutoRanging(false);
        xAxis.autosize();

        lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setAnimated(true);
        lineChart.autosize();
        lineChart.setTitle("This is my Line Chart");
        lineChart.getStyleClass().add("line-chart");
    }

    public Pane getLineChart() {
        layout = new HBox();
        layout.getChildren().add(lineChart);
        layout.getStyleClass().add("chartBox");
        layout.setAlignment(Pos.CENTER);
        return layout;
    }
}
