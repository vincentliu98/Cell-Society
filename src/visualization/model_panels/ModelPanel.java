package visualization.model_panels;

import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;

import java.util.Map;

/**
 *  For various model-specific panels that defines
 *  uniform theme across different panels. It is purposefully made
 *  abstract so that it would never actually be initialized.
 *
 *  ModelPanel contains a common parameter for all models - number of cells.
 *  It contains a Slider that dynamically change the number of cells and update the simulation
 *  It is also able to detect any parameter change that occurs in the UI and pass the change to the model
 *
 * @author Vincent Liu
 * @author Inchan Hwang
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

    /**
     *
     * @return
     */
    public int getCellNum() { return cellNum; }

    /**
     *
     * @return
     */
    public boolean isNumCellChanged() { return numCellChanged; }

    /**
     *
     * @return
     */
    public boolean isParamChanged() { return paramChanged; }

    /**
     *
     */
    public void cleanNumCellChanged() { numCellChanged = false; }

    /**
     *
     */
    public void cleanParamChanged() { paramChanged = false; }

    /**
     *
     * @return
     */
    public abstract Map<String, String> getParams();
}
