package visualization.model_panels;

import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;

import java.util.Map;

/**
 *  ModelPanel.
 *
 * @author Vincent Liu
 */
public abstract class ModelPanel extends VBox {
    public static final int DEFAULT_CELL_NUM = 10;

    protected boolean numCellChanged, paramChanged;

    private Slider numberBar = new Slider(0, 100, DEFAULT_CELL_NUM);
    private int cellNum;

    public static final Label cellNumCaption = new Label("Cell Number / Side:");
    private Label cellNumValue = new Label(
           Integer.toString((int) numberBar.getValue()));

    public ModelPanel() {
        super(25);
        getStyleClass().add("modelPanel");

        cellNum = DEFAULT_CELL_NUM;
        numberBar.setShowTickMarks(true);
        numberBar.setShowTickLabels(true);
        numberBar.setMajorTickUnit(20);
        numberBar.setSnapToTicks(true);
        numberBar.setBlockIncrement(4);

        numberBar.setOnMouseReleased(e -> {
            numCellChanged = true;
            cellNum = (int) numberBar.getValue();
            cellNumValue.setText(Integer.toString(cellNum));
        });

        numberBar.valueProperty().addListener((ov, old_val, new_val) -> {
            cellNumValue.setText(String.valueOf(new_val.intValue()));
        });

        getChildren().addAll(cellNumCaption, cellNumValue, numberBar);
    }

    public int getCellNum() { return cellNum; }
    public boolean isNumCellChanged() { return numCellChanged; }
    public boolean isParamChanged() { return paramChanged; }
    public void cleanNumCellChanged() { numCellChanged = false; }
    public void cleanParamChanged() { paramChanged = false; }

    public abstract Map<String, String> getParams();
}
