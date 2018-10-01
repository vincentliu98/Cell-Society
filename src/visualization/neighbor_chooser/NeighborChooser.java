package visualization.neighbor_chooser;

import javafx.scene.control.Dialog;
import javafx.util.Pair;
import java.util.List;

/**
 *  NeighborChooser mounts and delegates all operations to the NeighborChooserPane.
 */
public class NeighborChooser extends Dialog<List<Pair<Integer, Integer>>> {
    private NeighborChooserPane dialogPane;
    public NeighborChooser(String shapeType) {
        dialogPane = new NeighborChooserPane(shapeType);
        setDialogPane(dialogPane);
        setResultConverter(e -> {
            if(e.getButtonData().isCancelButton()) return null;
            else return dialogPane.getChosenIndices();
        });
    }
}
