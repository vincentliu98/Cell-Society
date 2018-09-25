package utility;

import javafx.scene.paint.Color;

/**
 * Create a mixture of two colors when the mouse is hovering above an individual cell on the simulation panel
 *
 * @author Inchan Hwang
 */

public class ColorUtils {
    /**
     *
     * @param a
     * @param b
     * @param pA
     * @return
     */
    public static Color mix(Color a, Color b, double pA) {
        double red = a.getRed()*pA + b.getRed()*(1-pA);
        double green = a.getGreen()*pA + b.getGreen()*(1-pA);
        double blue = a.getBlue()*pA + b.getBlue()*(1-pA);
        return new Color(red, green, blue, 1);
    }

    /**
     *
     * @param c
     * @return
     */
    public static Color invert(Color c) {
        return new Color(
                1-c.getRed(),
                1-c.getGreen(),
                1-c.getBlue(),
                1
        );
    }
}
