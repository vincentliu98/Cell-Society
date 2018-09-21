package simulation.models.wator;

import simulation.models.WaTorModel;

public class Fish {
    protected int breedCounter, actionCode;

    public Fish() { this(0); }
    public Fish(int breedCounter_) { breedCounter = breedCounter_; }

    public int tick(int breedPeriod, int starvePeriod) {
        actionCode = (++breedCounter > breedPeriod) ? WaTorModel.CODE_BREED : WaTorModel.CODE_NOTHING;
        return actionCode;
    }
    public void breed() { breedCounter = 0; }
    public void eat() { }
    public int kind() { return WaTorModel.FISH; }
}
