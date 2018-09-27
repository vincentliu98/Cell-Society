package visualization.statistics;

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
    public XYChart.Series Alive;
    private XYChart.Series Dead;
    private final NumberAxis xAxis = new NumberAxis();
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
        lineChart.setCreateSymbols(false);
        lineChart.getStyleClass().add("line-chart");

        Alive = new XYChart.Series();
        Alive.setName("Alive");
        Dead = new XYChart.Series();
        Dead.setName("Dead");

        lineChart.getData().addAll(Alive, Dead);
    }

    public Pane getChartBox() {
        HBox layout = new HBox();
        layout.getChildren().add(lineChart);
        layout.getStyleClass().add("chartBox");
        layout.setAlignment(Pos.CENTER);
        return layout;
    }

    public LineChart<Number, Number> getLineChart() {
        return lineChart;
    }
}
