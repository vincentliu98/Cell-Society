package simulation;

public class Shark {
    public static int sharkBreed = 10;
    public static int sharkStarve = 5;

    public String toString() {
        return "1";
    }

    public static int getSharkBreed() {
        return sharkBreed;
    }

    public static int getSharkStarve() {
        return sharkStarve;
    }

    public static void setSharkBreed(int sharkBreed) {
        Shark.sharkBreed = sharkBreed;
    }

    public static void setSharkStarve(int sharkStarve) {
        Shark.sharkStarve = sharkStarve;
    }
}
