package simulation;

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

    public void commit() { value = next; next = null; }

    public void updateView(SimulationModel<T> model) { view.setFill(model.chooseColor(value)); }

    public void handleClick(SimulationModel<T> model) { value = model.nextValue(value); updateView(model); }

    public T value() { return value; }

    public T next() { return next; }

    public int shapeCode() { return shapeCode; }

    public double cx() { return cx; }

    public double cy() { return cy; }

    public double width() { return width; }

    public double height() { return height; }

    public Shape view() { return view; }

    public void setNext(T next_) { next = next_; }
}