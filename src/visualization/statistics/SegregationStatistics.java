package visualization.statistics;

import javafx.scene.chart.XYChart;

import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author Vincent Liu
 */

public class SegregationStatistics extends ModelStatistics{
    public XYChart.Series Empty, Blue, Red;

    public SegregationStatistics() {
        super();
        Empty = new XYChart.Series();
        Empty.setName("Empty");
        Blue = new XYChart.Series();
        Blue.setName("Blue");
        Red = new XYChart.Series();
        Red.setName("Red");

        getData().addAll(Empty, Blue, Red);
    }

    @Override
    public void updateStatistics(Double durationCounter, ResourceBundle myResources, Map<String, Integer> newStatistics) {
        Empty.getData().add(new XYChart.Data<>(durationCounter, newStatistics.get("Empty")));
        Blue.getData().add(new XYChart.Data<>(durationCounter, newStatistics.get("Blue")));
        Red.getData().add(new XYChart.Data<>(durationCounter, newStatistics.get("Red")));
    }
}
