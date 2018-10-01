package utility;

import javafx.scene.paint.Color;

/**
 * Create a mixture of two colors when the mouse is hovering above an individual cell on the simulation panel
 *
 * @author Inchan Hwang
 */

public class ColorUtils {
    /**
     * Create a lighter version of the color of the cell that will be added if the mouse if clicked
     *
     * @param a
     * @param b
     * @param pA
     * @return a color suggesting the type of the cell that will be added
     */
    public static Color mix(Color a, Color b, double pA) {
        double red = a.getRed()*pA + b.getRed()*(1-pA);
        double green = a.getGreen()*pA + b.getGreen()*(1-pA);
        double blue = a.getBlue()*pA + b.getBlue()*(1-pA);
        return new Color(red, green, blue, 1);
    }
}
