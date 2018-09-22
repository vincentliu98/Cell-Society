package utility;

import javafx.scene.shape.Shape;

public class ShapeUtils {
    public static void centerShape(Shape s, double cx, double cy) {
        s.setLayoutX(cx - s.getLayoutBounds().getMinX() - s.getLayoutBounds().getWidth()/2);
        s.setLayoutY(cy - s.getLayoutBounds().getMinY() - s.getLayoutBounds().getHeight()/2);
    }
}
