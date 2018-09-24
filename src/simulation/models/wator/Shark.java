package simulation.models.wator;

import simulation.models.WaTorModel;

/**
 * @author Inchan Hwang
 */
public class Shark extends Fish {
    public Shark() { this(0, 0); }
    public Shark(int breedingCounter, int starveCounter_) {
        super(breedingCounter);
        starveCounter = starveCounter_;
    }

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
