package visualization.statistics;

import javafx.scene.chart.XYChart;

import java.util.Map;
import java.util.ResourceBundle;

public class WaTorStatistics extends ModelChart{
    public XYChart.Series Empty, Shark, Fish;

    WaTorStatistics() {
        super();
        Empty = new XYChart.Series();
        Empty.setName("Empty");
        Shark = new XYChart.Series();
        Shark.setName("Shark");
        Fish = new XYChart.Series();
        Fish.setName("Fish");

        getData().addAll(Empty, Shark, Fish);
    }

    @Override
    public void updateStatistics(Double durationCounter, ResourceBundle myResources, Map<String, Integer> newStatistics) {
        Empty.getData().add(new XYChart.Data<>(durationCounter, newStatistics.get("Empty")));
        Fish.getData().add(new XYChart.Data<>(durationCounter, newStatistics.get("Fish")));
        Shark.getData().add(new XYChart.Data<>(durationCounter, newStatistics.get("Shark")));

    }
}
