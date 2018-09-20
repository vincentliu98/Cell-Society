package simulation.models.wator;

import simulation.models.WaTorModel;

public class Fish {
    protected int breedCounter, actionCode;

    public Fish() { breedCounter = 0; }
    public int tick(int breedPeriod, int starvePeriod) {
        actionCode = (++breedCounter > breedPeriod) ? WaTorModel.CODE_BREED : WaTorModel.CODE_NOTHING;
        return actionCode;
    }
    public void breed() { breedCounter = 0; }
    public void eat() { }
    public int kind() { return WaTorModel.FISH; }
}
