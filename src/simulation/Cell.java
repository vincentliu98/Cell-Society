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

    public Cell(T value_, SimulationModel<T> model_, Shape view_, double cx_, double cy_) {
        value = value_; view = view_; next = null;
        view.setOnMouseClicked(e -> handleClick(model_));
        cx = cx_; cy = cy_;
        view.setLayoutX(cx - view.getLayoutBounds().getMinX() - ShapeUtils.meanX(view));
        view.setLayoutY(cy - view.getLayoutBounds().getMinY() - ShapeUtils.meanY(view));
    }

    public void commit() { value = next; next = null; }
    public void updateView(SimulationModel<T> model) { view.setFill(model.chooseColor(value)); }
    public void handleClick(SimulationModel<T> model) { value = model.nextValue(value); updateView(model); }

    public T value() { return value; }
    public T next() { return next; }
    public void setNext(T next_) { next = next_; }
    public Shape view() { return view; }
}