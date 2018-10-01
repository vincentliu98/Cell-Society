package visualization.neighbor_chooser;

import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.util.Pair;
import utility.ShapeUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class NeighborChooserPane extends DialogPane {
    public static final double DIALOG_WIDTH = 700;
    public static final double DIALOG_HEIGHT = 500;

    public static final double RECTANGLE_WIDTH = 100;
    public static final double RECTANGLE_HEIGHT = 100;
    public static final double TRIANGLE_WIDTH = 150;
    public static final double TRIANGLE_HEIGHT = 150;

    private ArrayList<IndexedShape> indexedShapes;

    public NeighborChooserPane(String shapeType) {
        setMinWidth(DIALOG_WIDTH);
        setMinHeight(DIALOG_HEIGHT);

        indexedShapes = new ArrayList<>();
        if (shapeType.equals(ShapeUtils.RECTANGULAR)) {
            initializeRectangle(indexedShapes);
        } else if (shapeType.equals(ShapeUtils.TRIANGULAR)) {
            initializeTriangle(indexedShapes);
        }
        getChildren().addAll(
            indexedShapes.stream().map(s -> s.shape()).collect(Collectors.toList())
        );

        getButtonTypes().add(ButtonType.CANCEL);
        getButtonTypes().add(ButtonType.APPLY);
    }

    public List<Pair<Integer, Integer>> getChosenIndices() {
        return indexedShapes
                .stream()
                .filter(s -> s.selected())
                .map(s -> s.offset())
                .collect(Collectors.toList());
    }

    private void initializeRectangle(ArrayList<IndexedShape> indexedShapes) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                indexedShapes.add(
                        new IndexedShape(
                                ShapeUtils.makeShape(
                                        ShapeUtils.RECTANGLE,
                                        DIALOG_WIDTH/2 + j * RECTANGLE_WIDTH,
                                        DIALOG_HEIGHT/2 + i * RECTANGLE_HEIGHT,
                                        RECTANGLE_WIDTH, RECTANGLE_HEIGHT
                                ), i, j)
                );
            }
        }
    }

    private void initializeTriangle(ArrayList<IndexedShape> indexedShapes) {
        // take a breath, and draw a triangular graph and you'll understand.
        for (int i = -1; i <= 1; i++) {
            for (int j = -2; j <= 2; j++) {
                if (i == -1 && (j == -2 || j == 2)) continue;
                indexedShapes.add(
                        new IndexedShape(
                                ShapeUtils.makeShape(
                                        (i + j) % 2 == 0 ? ShapeUtils.TRIANGLE : ShapeUtils.TRIANGLE_FLIP,
                                        DIALOG_WIDTH/2 + j * 0.5 * TRIANGLE_WIDTH,
                                        DIALOG_HEIGHT/2 + i * TRIANGLE_HEIGHT,
                                        TRIANGLE_WIDTH, TRIANGLE_HEIGHT
                                ), i, j)
                );
            }
        }
    }
}
