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
    private double cx, cy;

    public Cell(T value_, double cx_, double cy_) {
        value = value_; next = null;
        cx = cx_; cy = cy_;
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
    public Shape view() { return view; }

    /**
     *
     * @param next_
     */
    public void setNext(T next_) { next = next_; }

    /**
     *
     * @param view_
     */
    public void setShape(Shape view_) {
        view = view_;
        ShapeUtils.centerShape(view, cx, cy);
    }
}