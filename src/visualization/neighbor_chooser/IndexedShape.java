package visualization.neighbor_chooser;

import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.util.Pair;

import static simulation.Simulator.MOUSE_ENTER_OPACITY;

/**
 *   IndexedShape is a convenience class for NeighborChooserPane, wrapping
 *   shape and offset info together, and handling mouse clicks for selection.
 */
public class IndexedShape {
    private Shape shape;
    private int rowOffset, columnOffset;
    private boolean selected;
    private Color colorTheme;

    public IndexedShape(Shape shape_, Integer rowOffset_, Integer columnOffset_) {
        shape = shape_;
        rowOffset = rowOffset_;
        columnOffset = columnOffset_;
        selected = true;
        colorTheme = (rowOffset + columnOffset)%2 == 0 ? Color.GOLD : Color.GREEN;
        updateColor(false);

        if(rowOffset != 0 || columnOffset != 0) {
            shape.setOnMouseEntered(e -> {
                shape.toFront();
                shape.setStrokeDashOffset(5);
                updateColor(true);
                shape.getStrokeDashArray().addAll(10d);
                shape.setOpacity(MOUSE_ENTER_OPACITY);
            });
            shape.setOnMouseExited(e -> {
                updateColor(false);
                shape.setStroke(new Color(0, 0, 0, 0));
                shape.setOpacity(1);
            });
            shape.setOnMouseClicked(e -> toggleSelection());
        }
    }

    public Shape shape() { return shape; }
    public Pair<Integer, Integer> offset() { return new Pair<>(rowOffset, columnOffset); }
    public boolean selected() { return selected; }

    public void toggleSelection() {
        selected = !selected;
        updateColor(false);
    }
    private void updateColor(boolean inv) {
        shape.setFill(inv ^ selected ? colorTheme : colorTheme.darker().darker().darker());
    }

}

