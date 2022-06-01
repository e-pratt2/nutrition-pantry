package Database;

import java.io.Serializable;
import java.util.Objects;

/**
 * A database class that creates a grocery object that holds the nutrition information and a name
 */
public class Grocery implements Serializable {

    private String name;
    private Nutrition nutrition;

    /**
     * Grocery constructor creates a Grocery object
     * @param name a string name of the grocery
     * @param nutrition a Nutrition object associated with the grocery
     */
    public Grocery(String name, Nutrition nutrition) {
        if(name == null || name.equalsIgnoreCase("") || nutrition == null)
            throw new IllegalArgumentException("Bad param in Grocery");
        this.name = name;
        this.nutrition = nutrition;
    }

    /**
     * returns the Nutrition field
     * @return returns the nutrition of this Grocery
     */
    public Nutrition getNutrition() {
        return nutrition;
    }

    /**
     * returns the Name field
     * @return returns the name of this Grocery
     */
    public String getName() {
        return name;
    }

    /**
     * this is the hash method
     * @return returns the hashcode of the name and the nutrition fields
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, nutrition);
    }

    /**
     *
     * @return returns Grocery{ the name of the grocery, the nutrition information}
     */
    @Override
    public String toString() {
        return "Grocery{" + name + ", " + nutrition.toString() + "}";
    }

    /**
     * checks if the object passed in equals to this grocery object
     * @param obj the object that this Grocery is compared to
     * @return returns true if the objects are the same
     * @return returns false if the objects are different or if the object passed in is not a Grocery object
     */
    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof Grocery))
            return false;

        Grocery g = (Grocery) obj;

        if(this.getName().equalsIgnoreCase(g.getName()) && this.getNutrition().equals(g.getNutrition()))
            return true;
        return false;
    }
}
