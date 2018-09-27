package simulation;

import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import simulation.models.SimulationModel;
import utility.ShapeUtils;

/**
 * Cell implements a basic unit of CA.
 *
 * @param <T> The type of the value that this cell holds
 * @author Inchan Hwang
 */

public class Cell<T> {
    private T value, next;
    private Shape view;

    private int shapeCode;
    private double cx, cy, width, height;

    public Cell(T value_, int shapeCode_, double cx_, double cy_, double width_, double height_) {
        value = value_; next = null;
        shapeCode = shapeCode_;
        cx = cx_; cy = cy_; width = width_; height = height_;
        view = ShapeUtils.makeShape(shapeCode, cx, cy, width, height);
    }

    /**
     *
     */
    public void commit() { value = next; next = null; }

    /**
     *
     * @param model
     */
    public void updateView(SimulationModel<T> model) { view.setFill(model.chooseColor(value)); }

    /**
     *
     * @param model
     */
    public void handleClick(SimulationModel<T> model) { value = model.nextValue(value); updateView(model); }

    /**
     *
     * @return
     */
    public T value() { return value; }

    /**
     *
     * @return
     */
    public T next() { return next; }

    /**
     *
     * @return
     */
    public int shapeCode() { return shapeCode; }

    /**
     *
     * @return
     */
    public double cx() { return cx; }

    /**
     *
     * @return
     */
    public double cy() { return cy; }

    /**
     *
     * @return
     */
    public double width() { return width; }

    /**
     *
     * @return
     */
    public double height() { return height; }

    /**
     *
     * @return
     */
    public Shape view() { return view; }

    /**
     *
     * @param next_
     */
    public void setNext(T next_) { next = next_; }
}