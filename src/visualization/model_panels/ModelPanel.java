package visualization.model_panels;

import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 *   For various model-specific panels that defines
 *   uniform theme across different panels. It is purposefully made
 *   abstract so that it would never actually be initialized.
 */
public abstract class ModelPanel extends VBox {
    public static final Text TITLE = new Text("Model Panel");
    public ModelPanel() {
        super(25, TITLE);
    }
}
