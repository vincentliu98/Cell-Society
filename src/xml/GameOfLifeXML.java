package xml;

import java.util.List;

/**
 * Simple immutable value object representing Game Of Life data.
 *
 * @author Robert C. Duvall
 * @author Vincent Liu
 */
public class GameOfLifeXML {
    // name in data file that will indicate it represents data for this type of object
    public static final String DATA_TYPE = "GameOfLife";
    // field names expected to appear in data file holding values for this object

    // specific data values for this instance
    private double myX;
    private double myY;
    private Integer myUniqueID;
    private Integer isAlive;
    private List<Integer> myNeighbors;


    // provide getters, not setters
    public double getMyX () {
        return myX;
    }

    public double getMyY () {
        return myY;
    }

    public Integer getMyUniqueID() {
        return myUniqueID;
    }

    public List<Integer> getMyNeighbors() {
        return myNeighbors;
    }

    public Integer getIsAlive() {
        return isAlive;
    }

    public void setMyUniqueID(Integer myUniqueID) {
        this.myUniqueID = myUniqueID;
    }

    public void setMyY(double myY) {
        this.myY = myY;
    }

    public void setMyX(double myX) {
        this.myX = myX;
    }

    public void setMyNeighbors(List<Integer> myNeighbors) {
        this.myNeighbors = myNeighbors;
    }

    public void setIsAlive(Integer isAlive) {
        this.isAlive = isAlive;
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString () {
        return  "Cell:: uniqueID = " + this.myUniqueID + " x = " + this.myX + " y = " + this.myY +
                " neighbors = " + this.myNeighbors + " isAlive = " + this.isAlive;
    }
}
