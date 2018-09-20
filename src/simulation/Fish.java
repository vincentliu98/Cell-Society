package simulation;

public class Fish {
    public static int fishBreed = 5;
    public static int fishStarve = 3;

    public String toString() {
        return "1";
    }

    public static int getFishBreed() {
        return fishBreed;
    }

    public static int getFishStarve() {
        return fishStarve;
    }

    public static void setFishBreed(int fishBreed) {
        Fish.fishBreed = fishBreed;
    }

    public static void setFishStarve(int fishStarve) {
        Fish.fishStarve = fishStarve;
    }
}
