package xml.writer;

import org.w3c.dom.Element;
import simulation.CellGraph;
import simulation.models.WaTorModel;
import simulation.models.wator.Fish;

import java.io.File;
import java.util.List;

/**
 *
 * @author Inchan Hwang
 */

public class WaTorWriter extends XMLWriter<Fish> {
    private WaTorModel wator;
    public WaTorWriter(WaTorModel sim_, CellGraph<Fish> graph_, File outFile_) {
        super(sim_, graph_, outFile_);
        wator = sim_;
    }

    @Override
    protected List<Element> encodeCellValue(Fish value) {
        var kind = doc.createElement("kind");
        var breedCounter = doc.createElement("breedCounter");
        var starveCounter = doc.createElement("starveCounter");

        if(value == null) {
            kind.appendChild(doc.createTextNode(Integer.toString(WaTorModel.EMPTY)));
            return List.of(kind);
        }

        kind.appendChild(doc.createTextNode(Integer.toString(value.kind())));
        breedCounter.appendChild(doc.createTextNode(Integer.toString(value.breedCounter())));
        starveCounter.appendChild(doc.createTextNode(Integer.toString(value.starveCounter())));
        return List.of(kind, breedCounter, starveCounter);
    }

    @Override
    protected List<Element> parseModelParams() {
        var fishBreedPeriod = doc.createElement("fishBreedPeriod");
        var sharkBreedPeriod = doc.createElement("sharkBreedPeriod");
        var sharkStarvePeriod = doc.createElement("sharkStarvePeriod");
        fishBreedPeriod.appendChild(doc.createTextNode(Integer.toString(wator.getFishBreedPeriod())));
        sharkBreedPeriod.appendChild(doc.createTextNode(Integer.toString(wator.getSharkBreedPeriod())));
        sharkStarvePeriod.appendChild(doc.createTextNode(Integer.toString(wator.getSharkStarvePeriod())));
        return List.of(fishBreedPeriod, sharkBreedPeriod, sharkStarvePeriod);
    }
}

