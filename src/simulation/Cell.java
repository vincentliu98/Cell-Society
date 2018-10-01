package simulation;

import javafx.scene.shape.Shape;
import simulation.models.SimulationModel;
import utility.ShapeUtils;

/**
 * Cell implements a basic unit of CA, and holds information of its
 * internal value and shape/position.
 *
 * @param <T> The type of the value that this cell holds
 * @author Inchan Hwang
 */

public class Cell<T> {
    private T value, next;
    private Shape view;

    private int shapeCode;
    private double cx, cy, width, height;

    /**
     * Initialize cells
     *
     * @param value_
     * @param shapeCode_
     * @param cx_
     * @param cy_
     * @param width_
     * @param height_
     */
    public Cell(T value_, int shapeCode_, double cx_, double cy_, double width_, double height_) {
        value = value_; next = null;
        shapeCode = shapeCode_;
        cx = cx_; cy = cy_; width = width_; height = height_;
        view = ShapeUtils.makeShape(shapeCode, cx, cy, width, height);
    }

    /**
     * Replace the current value with the next value
     */
    public void commit() { value = next; next = null; }

    /**
     * Update the color of the cell
     * @param model
     */
    public void updateView(SimulationModel<T> model) { view.setFill(model.chooseColor(value)); }

    /**
     * Update the cell after the click
     *  @param model
     */
    public void handleClick(SimulationModel<T> model) { value = model.nextValue(value); updateView(model); }

    /**
     * @return the current value of the cell
     */
    public T value() { return value; }

    /**
     * @return the next value of the cell
     */
    public T next() { return next; }

    /**
     * @return the shapeCode of the cell
     */
    public int shapeCode() { return shapeCode; }

    /**
     * @return the x position of the cell
     */
    public double cx() { return cx; }

    /**
     * @return the y position of the cell
     */
    public double cy() { return cy; }

    /**
     * @return the width of the cell
     */
    public double width() { return width; }

    /**
     * @return the height of the cell
     */
    public double height() { return height; }

    /**
     * @return the shape of the
     */
    public Shape view() { return view; }

    /**
     * Set the next value to next_
     *
     * @param next_
     */
    public void setNext(T next_) { next = next_; }
}