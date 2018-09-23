package visualization.model_panels;

import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;

/**
 *   For various model-specific panels that defines
 *   uniform theme across different panels. It is purposefully made
 *   abstract so that it would never actually be initialized.
 *
 * @author Vincent Liu
 */

public abstract class ModelPanel extends VBox {
    public static final int DEFAULT_CELL_NUM = 10;
    private Slider numberBar = new Slider(0, 100, DEFAULT_CELL_NUM);
    private int cellNum;
    private boolean changeCellNum;

    public static final Label cellNumCaption = new Label("Cell Number / Side:");
    private Label cellNumValue = new Label(
           Integer.toString((int) numberBar.getValue()));

    public ModelPanel() {
        super(25);
        numberBar.setShowTickMarks(true);
        numberBar.setShowTickLabels(true);
        numberBar.setMajorTickUnit(20);
        numberBar.setSnapToTicks(true);
        numberBar.setBlockIncrement(4);

        numberBar.valueProperty().addListener((ov, old_val, new_val) -> {
            changeCellNum = true;
            cellNum = new_val.intValue();
            cellNumValue.setText(Integer.toString(cellNum));
        });
        getChildren().addAll(cellNumCaption, cellNumValue, numberBar);
    }

    public int getCellNum() {
        return cellNum;
    }

    public void setChangeCellNum(boolean changeCellNum) {
        this.changeCellNum = changeCellNum;
    }

    public boolean getChangeCellNum() {
        return changeCellNum;
    }


}
