package simulation;

import javafx.scene.shape.Shape;
import simulation.rules.UpdateRule;

import java.util.List;

/**
 * Cell implements a basic unit of CA.
 * It holds two things - a value and its view
 * It updates its value using an UpdateRule instance, and keeps it in the variable "next".
 * The update is applied only when commit() is called.
 * Again, using an UpdateRule instance, it updates its Color based on its value
 *
 * @param <T> The type of the value that this cell holds
 * @author Inchan Hwang
 */
public class Cell<T> {
    private T value, next;
    private Shape view;

    public Cell(T value_, Shape view_) {
        value = value_; view = view_;
    }

    public void update(UpdateRule<T> rule, List<T> neighborVals) { next = rule.nextValue(value, neighborVals); }
    public void commit() { value = next; }
    public void updateView(UpdateRule<T> rule) { view.setFill(rule.chooseColor(value)); }
    public void setNext(T next_) { next = next_; }

    public T value() { return value; }
    public T next() { return next; }
    public Shape view() { return view; }
}