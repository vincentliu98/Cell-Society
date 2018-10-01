package simulation.models;

import javafx.scene.paint.Color;
import simulation.Cell;
import simulation.CellGraph;
import simulation.models.wator.Fish;
import simulation.models.wator.Shark;
import xml.writer.WaTorWriter;
import xml.writer.XMLWriter;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

/**
 * WaTorModel implements SimulationModel interface.
 *
 * @author Inchan Hwang
 */

public class WaTorModel implements SimulationModel<Fish> {
    public static final int FISH = 0;
    public static final int SHARK = 1;
    public static final int EMPTY = 2;

    public static final int CODE_NOTHING = 0;
    public static final int CODE_BREED = 1;
    public static final int CODE_STARVE = 2;

    public static final String MODEL_NAME = "Wa-Tor";
    public static final String PARAM_FISHBREED = "fishBreedPeriod";
    public static final String PARAM_SHARKBREED = "sharkBreedPeriod";
    public static final String PARAM_SHARKSTARVE = "sharkStarvePeriod";

    private int fishBreedPeriod, sharkBreedPeriod, sharkStarvePeriod;

    public WaTorModel(int fishBreedPeriod_, int sharkBreedPeriod_, int sharkStarvePeriod_) {
        fishBreedPeriod = fishBreedPeriod_;
        sharkBreedPeriod = sharkBreedPeriod_;
        sharkStarvePeriod = sharkStarvePeriod_;
    }

    @Override
    public int updatePriority(Fish myVal) {
        return myVal == null ? 2 :
                myVal.kind() == SHARK ? 0 : 1;
    }

    @Override
    public void localUpdate(Cell<Fish> me, List<Cell<Fish>> neighbors) {
        if (me.value() == null) return;

        var ac = me.value().tick(me.value().kind() == FISH ? fishBreedPeriod : sharkBreedPeriod,
                                  me.value().kind() == FISH ? 0 : sharkStarvePeriod);

        if(ac == CODE_STARVE) return;

        if(me.value().kind() == FISH) handleFish(me, neighbors, ac);
        else handleShark(me, neighbors, ac);
    }

    private void handleFish(Cell<Fish> me, List<Cell<Fish>> neighbors, int actionCode) {
        if(me.next() != null && me.next().kind() == SHARK) return; // eaten by shark
        var emptyCell = pickEmpty(neighbors);
        if(emptyCell == null) return;
        emptyCell.setNext(me.value());

        if(actionCode == CODE_BREED && me.next() == null) {
            me.value().breed();
            me.setNext(new Fish());
        }
    }

    private void handleShark(Cell<Fish> me, List<Cell<Fish>> neighbors, int actionCode) {
        var fishCell = pickFish(neighbors);
        var emptyCell = pickEmpty(neighbors);

        Cell<Fish> moveTo;
        if(fishCell != null) {
            me.value().eat();
            moveTo = fishCell;
        } else if(emptyCell != null) moveTo = emptyCell;
        else { me.setNext(me.value()); return; }

        moveTo.setNext(me.value());

        if(actionCode == CODE_BREED && me.next() == null) {
            me.value().breed();
            me.setNext(new Shark());
        }
    }

    private Cell<Fish> pickFish(List<Cell<Fish>> neighbors) {
        var t = neighbors.stream().filter(c ->
                (c.value() != null) && c.value().kind() == FISH).collect(Collectors.toList());
        return t.size() == 0 ? null : t.get(new Random().nextInt(t.size()));
    }

    private Cell<Fish> pickEmpty(List<Cell<Fish>> neighbors) {
       var t = neighbors.stream().filter(c -> c.next() == null).collect(Collectors.toList());
       return t.size() == 0 ? null : t.get(new Random().nextInt(t.size()));
    }

    @Override
    public void globalUpdate(CellGraph<Fish> graph) {
        // waTor model doesn't require global update
    }

    @Override
    public Fish nextValue(Fish myVal) {
        return myVal == null ? new Fish() :
                myVal.kind() == FISH ? new Shark() : null;
    }

    @Override
    public Color chooseColor(Fish myVal) {
       return myVal == null ? Color.LIGHTCYAN :
               myVal.kind() == FISH ? Color.LIGHTGREEN : Color.BLUE;
    }

    @Override
    public String modelName() { return MODEL_NAME; }

    @Override
    public Map<String, Integer> getStatistics(List<Fish> values) {
        HashMap<String, Integer> myMap = new HashMap<>();
        int fishNum = 0;
        int sharkNum = 0;
        int emptyNum = 0;
        for (Fish a : values) {
            if (a == null) emptyNum++;
            else if (a.kind() == SHARK) sharkNum++;
            else fishNum++;
        }
        myMap.put("Empty", emptyNum);
        myMap.put("Fish", fishNum);
        myMap.put("Shark", sharkNum);
        return myMap;
    }

    @Override
    public XMLWriter<Fish> getXMLWriter(CellGraph<Fish> graph, File outFile, String language) {
        return new WaTorWriter(this, graph, outFile, language);
    }

    @Override
    public void updateModelParams(Map<String, String> params) {
        fishBreedPeriod = Integer.parseInt(params.get(PARAM_FISHBREED));
        sharkBreedPeriod = Integer.parseInt(params.get(PARAM_SHARKBREED));
        sharkStarvePeriod = Integer.parseInt(params.get(PARAM_SHARKSTARVE));
    }

    public int getFishBreedPeriod() { return fishBreedPeriod; }
    public int getSharkBreedPeriod() { return sharkBreedPeriod; }
    public int getSharkStarvePeriod() { return sharkStarvePeriod; }
}
