package simulation;

import javafx.scene.shape.Shape;
import simulation.rules.UpdateRule;

import java.util.ArrayList;

public class Cell<T> {
    private T value, next;
    private Shape view;

    public Cell(T value_, Shape view_) {
        value = value_; view = view_;
    }

    public void update(UpdateRule<T> rule, ArrayList<Cell<T>> neighbors) { next = rule.nextValue(this, neighbors); }
    public void commit() { value = next; }
    public void updateView(UpdateRule<T> rule) { view.setFill(rule.chooseColor(this)); }

    public T value() { return value; }
    public Shape view() { return view; }
}