package simulation.models.wator;

import simulation.models.WaTorModel;

public class Shark extends Fish {
    private int starveCounter;
    public Shark() { super(); starveCounter = 0; }

    @Override
    public int tick(int breedPeriod, int starvePeriod) {
        super.tick(breedPeriod, starvePeriod);
        actionCode = (++ starveCounter > starvePeriod) ? WaTorModel.CODE_STARVE : actionCode;
        return actionCode;
    }

    @Override
    public void eat() { starveCounter = 0; }

    @Override
    public int kind() { return WaTorModel.SHARK; }
}