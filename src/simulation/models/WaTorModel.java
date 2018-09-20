package simulation.models;

import javafx.scene.paint.Color;
import simulation.Cell;
import simulation.CellGraph;
import simulation.models.wator.Fish;
import simulation.models.wator.Shark;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 *  WaTor Model
 *
 * @author Inchan Hwang
 */

public class WaTorModel implements SimulationModel<Fish> {
    public static final int FISH = 0;
    public static final int SHARK = 1;

    public static final int CODE_NOTHING = 0;
    public static final int CODE_BREED = 1;
    public static final int CODE_STARVE = 2;

    public static final String MODEL_NAME = "Wa-Tor";

    private int fishBreedPeriod, sharkBreedPeriod, sharkStarvePeriod;

    public WaTorModel(int fishBreedPeriod_, int sharkBreedPeriod_, int sharkStarvePeriod_) {
        fishBreedPeriod = fishBreedPeriod_;
        sharkBreedPeriod = sharkBreedPeriod_;
        sharkStarvePeriod = sharkStarvePeriod_;
    }

    @Override
    public void localUpdate(Cell<Fish> me, List<Cell<Fish>> neighbors) {
        if (me.value() == null) return;

        var ac = me.value().tick(me.value().kind() == FISH ? fishBreedPeriod : sharkBreedPeriod,
                                  me.value().kind() == FISH ? 0 : sharkStarvePeriod);

        if(ac == CODE_STARVE) { me.setNext(null); return; }

        if(me.value().kind() == FISH) handleFish(me, neighbors, ac);
        else handleShark(me, neighbors, ac);
    }

    private void handleFish(Cell<Fish> me, List<Cell<Fish>> neighbors, int actionCode) {
        var emptyCell = pickEmpty(neighbors);
        if(emptyCell == null) { me.setNext(me.value()); return; }
        emptyCell.setNext(me.value());
        if(actionCode == CODE_BREED) {
            me.value().breed();
            me.setNext(new Fish());
        } else me.setNext(null);
    }

    private void handleShark(Cell<Fish> me, List<Cell<Fish>> neighbors, int actionCode) {
        Cell<Fish> moveTo = null;
        var fishCell = pickFish(neighbors);
        var emptyCell = pickEmpty(neighbors);

        if(fishCell != null) {
            me.value().eat();
            moveTo = fishCell;
        } else if(emptyCell != null) moveTo = emptyCell;

        if(moveTo == null) { System.out.println("stuck"); me.setNext(me.value()); return; }
        moveTo.setNext(me.value());
        System.out.println("move");

        if(actionCode == CODE_BREED) {
            me.value().breed();
            me.setNext(new Shark());
        } else me.setNext(null);
    }

    private Cell<Fish> pickFish(List<Cell<Fish>> neighbors) {
        var t = neighbors.stream().filter(c ->
                (c.next() != null) && c.next().kind() == FISH).collect(Collectors.toList());
        return t.size() == 0 ? null : t.get(new Random().nextInt(t.size()));
    }

    private Cell<Fish> pickEmpty(List<Cell<Fish>> neighbors) {
       var t = neighbors.stream().filter(c -> c.next() == null).collect(Collectors.toList());
       return t.size() == 0 ? null : t.get(new Random().nextInt(t.size()));
    }


    @Override
    public void globalUpdate(CellGraph<Fish> graph) { }

    @Override
    public Fish nextValue(Fish myVal) {
        return myVal.kind() == FISH ? new Shark() : new Fish();
    }

    @Override
    public Color chooseColor(Fish myVal) {
       return myVal == null ? Color.LIGHTCYAN :
               myVal.kind() == FISH ? Color.BEIGE : Color.BLUE;
    }

    @Override
    public String modelName() { return MODEL_NAME; }

}
