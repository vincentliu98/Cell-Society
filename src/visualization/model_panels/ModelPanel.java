package visualization.model_panels;

import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 *   For various model-specific panels that defines
 *   uniform theme across different panels. It is purposefully made
 *   abstract so that it would never actually be initialized.
 *
 * @author Inchan Hwang
 */
public abstract class ModelPanel extends VBox {
    public static final Text TITLE = new Text("Parameters");
    public static final Text NUMBER_BAR_TEXT = new Text("Cell Numbers");


    private Slider numberBar;
    private int cellNum;


    public ModelPanel() {
        super(25, TITLE);
        numberBar = new Slider(100, 10000, 100);
        cellNum = (int) numberBar.getValue();
        getChildren().addAll(numberBar);
    }

    public int getCellNum() {
        return cellNum;
    }

    public void setCellNum(int cellNum) {
        this.cellNum = cellNum;
    }
}
