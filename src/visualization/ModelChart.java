package visualization;

import javafx.application.Application;
import javafx.collections.*;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.chart.*;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

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
    private Pane layout = new HBox();
    private NumberAxis xAxis = new NumberAxis();
    private NumberAxis yAxis = new NumberAxis();
    private HBox chartBox;

    public ModelChart() {
        xAxis.setLabel("Time (s)");
        yAxis.setLabel("Number");
        xAxis.setMinorTickVisible(false);
        xAxis.setAutoRanging(false);
        xAxis.setTickUnit(1);

        lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.getStyleClass().add("line-chart");
        lineChart.setAnimated(true);
        lineChart.setTitle("This is my Line Chart");
    }

    public HBox getLineChart() {
        chartBox = new HBox();
        chartBox.getStyleClass().add("chart-box");
        chartBox.getChildren().add(lineChart);
        return chartBox;
    }
}
