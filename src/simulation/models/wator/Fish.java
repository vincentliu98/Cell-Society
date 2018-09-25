package simulation.models.wator;

import simulation.models.WaTorModel;

/**
 * Fish class to represent fish object. It's the superclass of Shark, as they have similar functions.
 * The counters keep track of the action to breed or starve.
 *
 * @author Inchan Hwang
 */
public class Fish {
    protected int starveCounter, breedCounter, actionCode;

    public Fish() { this(0); }
    public Fish(int breedCounter_) { breedCounter = breedCounter_; starveCounter = 0; }

    /**
     *
     * @param breedPeriod
     * @param starvePeriod
     * @return
     */
    public int tick(int breedPeriod, int starvePeriod) {
        actionCode = (++breedCounter > breedPeriod) ? WaTorModel.CODE_BREED : WaTorModel.CODE_NOTHING;
        return actionCode;
    }

    /**
     *
     */
    public void breed() { breedCounter = 0; }

    public void eat() { }

    /**
     *
     * @return
     */
    public int kind() { return WaTorModel.FISH; }

    /**
     *
     * @return
     */
    public int breedCounter() { return breedCounter; }

    /**
     *
     * @return
     */
    public int starveCounter() { return starveCounter; }
}
