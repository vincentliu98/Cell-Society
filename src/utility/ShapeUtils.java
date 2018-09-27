package utility;

import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import simulation.Simulator;

/**
 * ???
 *
 * @author Inchan Hwang
 */

public class ShapeUtils {
    public static final String RECTANGULAR = "rectangular";
    public static final String TRIANGULAR = "triangular";
    public static final String[] SHAPES = new String[] {
            RECTANGULAR,
            TRIANGULAR
    };

    public static final int RECTANGLE = 0;
    public static final int TRIANGLE = 1;
    public static final int TRIANGLE_FLIP = 2;

    public static void centerShape(Shape s, double cx, double cy) {
        s.setLayoutX(cx - s.getLayoutBounds().getMinX() - s.getLayoutBounds().getWidth()/2);
        s.setLayoutY(cy - s.getLayoutBounds().getMinY() - s.getLayoutBounds().getHeight()/2);
    }

    public static Shape makeShape(int shapeCode, double cx, double cy, double w, double h) {
        Shape ret;
        if(shapeCode == TRIANGLE) {
            var p = new Polygon();
            p.getPoints().addAll(
                    0., h,  // left
                    w, h, // right
                    0.5*w, 0. // top
            ); ret = p;
        } else if(shapeCode == TRIANGLE_FLIP) {
            var p = new Polygon();
            p.getPoints().addAll(
                    0., 0.,  // left
                    w, 0., // right
                    0.5*w, h // bottom
            ); ret =  p;
        } else ret = new Rectangle(w, h);
        centerShape(ret, cx, cy);

        return Shape.intersect(ret,
                new Rectangle(0, 0, Simulator.SIMULATION_SX, Simulator.SIMULATION_SY));
    }
}
