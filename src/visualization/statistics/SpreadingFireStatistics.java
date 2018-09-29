package visualization.statistics;

import javafx.scene.chart.XYChart;

import java.util.Map;
import java.util.ResourceBundle;

/**
 *  A Line Chart to display and update statistics for SpreadingFireModel
 *
 * @author Vincent Liu
 */

public class SpreadingFireStatistics extends ModelStatistics{
    public XYChart.Series Empty, Tree, Burning;

    /**
     * Initialized the three Series
     * <ul>
     *     <li>Empty</li>
     *     <li>Tree</li>
     *     <li>Burning</li>
     * </ul>
     */
    public SpreadingFireStatistics() {
        super();
        Empty = new XYChart.Series();
        Empty.setName("Empty");
        Tree = new XYChart.Series();
        Tree.setName("Tree");
        Burning = new XYChart.Series();
        Burning.setName("Burning");

        getData().addAll(Empty, Tree, Burning);
    }

    @Override
    public void updateStatistics(Double durationCounter, ResourceBundle myResources, Map<String, Integer> newStatistics) {
        Empty.getData().add(new XYChart.Data<>(durationCounter, newStatistics.get("Empty")));
        Tree.getData().add(new XYChart.Data<>(durationCounter, newStatistics.get("Tree")));
        Burning.getData().add(new XYChart.Data<>(durationCounter, newStatistics.get("Burning")));

    }
}
