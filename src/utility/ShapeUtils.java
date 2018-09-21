package utility;

import javafx.scene.shape.Shape;

public class ShapeUtils {
    public static double meanX(Shape s) {
        return s.getLayoutBounds().getMinX() + s.getLayoutBounds().getWidth()/2;
    }
    public static double meanY(Shape s) {
        return s.getLayoutBounds().getMinY() + s.getLayoutBounds().getHeight()/2;
    }
}
