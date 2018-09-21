package utility;

import javafx.scene.shape.Shape;

public class ShapeUtils {
    public static void centerShape(Shape s, double cx, double cy) {
        s.setLayoutX(cx - s.getLayoutBounds().getMinX() - ShapeUtils.meanX(s));
        s.setLayoutY(cy - s.getLayoutBounds().getMinY() - ShapeUtils.meanY(s));
    }
    private static double meanX(Shape s) { return s.getLayoutBounds().getMinX() + s.getLayoutBounds().getWidth()/2; }
    private static double meanY(Shape s) { return s.getLayoutBounds().getMinY() + s.getLayoutBounds().getHeight()/2; }
}
