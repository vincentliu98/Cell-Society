package simulation.rules;

import javafx.scene.paint.Color;
import simulation.Cell;
import utility.IntegerPair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 *  SegregationRule implements Segregation Model.
 *  The integer pair represents (STAY/LEAVE, RED/BLUE)
 */
public class SegregationRule implements UpdateRule<IntegerPair> {
    public static final int STAY = 0;
    public static final int LEAVE = 1;
    public static final int EMPTY = 0;
    public static final int BLUE = 1;
    public static final int RED = 2;

    public static final String MODEL_NAME = "Segregation";

    private double tolerance;
    public SegregationRule(double tolerance_) { tolerance = tolerance_; }

    @Override
    public IntegerPair nextValue(IntegerPair myVal, List<IntegerPair> neighborVal) {
        long nDiff = neighborVal.stream().filter(c ->
                c.getValue() > 0 || (!c.getValue().equals(myVal.getValue()))).count();
        return new IntegerPair(
                (nDiff/((double) neighborVal.size()) > tolerance || myVal.getValue() == EMPTY) ? LEAVE : STAY,
                myVal.getValue());
    }

    @Override
    public Color chooseColor(IntegerPair myVal) {
        return myVal.getValue() == RED ? Color.RED:
                myVal.getValue() == BLUE ? Color.BLUE:
                 myVal.getValue() == EMPTY ? Color.WHITE : Color.BLACK;
    }

    @Override
    public void beforeCommit(List<Cell<IntegerPair>> cells) {
        var leavers = cells.stream().filter(c -> c.next().getKey() == LEAVE).collect(Collectors.toList());
        ArrayList<Integer> colors = new ArrayList<>();
        for(var leaver: leavers) colors.add(leaver.next().getValue());

        Collections.shuffle(colors);
        for(int i = 0 ; i < leavers.size() ; i ++) {
            leavers.get(i).setNext(new IntegerPair(leavers.get(i).next().getKey(), colors.get(i)));
        }
    }

    @Override
    public String modelName() { return MODEL_NAME; }
}
