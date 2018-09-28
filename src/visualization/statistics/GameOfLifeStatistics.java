package visualization.statistics;

import javafx.scene.chart.XYChart;

import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author Vincent Liu
 */

public class GameOfLifeStatistics extends ModelStatistics{
    public XYChart.Series Alive, Dead;

    public GameOfLifeStatistics() {
        super();
        Alive = new XYChart.Series();
        Alive.setName("Alive");
        Dead = new XYChart.Series();
        Dead.setName("Dead");

        getData().addAll(Alive, Dead);
    }

    @Override
    public void updateStatistics(Double durationCounter, ResourceBundle myResources, Map<String, Integer> newStatistics) {
        Alive.getData().add(new XYChart.Data<>(durationCounter, newStatistics.get("Alive")));
        Dead.getData().add(new XYChart.Data<>(durationCounter, newStatistics.get("Dead")));
    }



}
