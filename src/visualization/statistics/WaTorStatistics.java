package visualization.statistics;

import javafx.scene.chart.XYChart;

import java.util.Map;
import java.util.ResourceBundle;

/**
 *  A Line Chart to display and update statistics for WaTorModel
 *
 * @author Vincent Liu
 */

public class WaTorStatistics extends ModelStatistics{
    public XYChart.Series Empty, Shark, Fish;

    /**
     * Initialized the three Series
     * <ul>
     *     <li>Empty</li>
     *     <li>Shark</li>
     *     <li>Fish</li>
     * </ul>
     */
    public WaTorStatistics() {
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
