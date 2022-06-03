package Database;

import java.io.Serializable;
import java.util.Objects;

/**
 * A database class that the nutrition information and a name of a grocery
 */
public class Grocery implements Serializable {

    private final String name;
    private final Nutrition nutrition;
    private final double servings;

    /**
     * Grocery constructor creates a Grocery object
     * @param name a string name of the grocery
     * @param nutrition a Nutrition object associated with the grocery
     */
    public Grocery(String name, Nutrition nutrition, double servings) {
        if(name == null || name.equalsIgnoreCase("") || nutrition == null || servings <= 0.0)
            throw new IllegalArgumentException("Bad param in Grocery");
        this.name = name;
        this.nutrition = nutrition;
        this.servings = servings;
    }

    /**
     * returns the Nutrition field
     * @return returns the nutrition of this Grocery
     */
    public Nutrition getNutritionPerServing() {
        return nutrition;
    }

    public Nutrition getTotalNutrition() {
        return nutrition.multiply(servings);
    }

    public double getServingsPerContainer() {
        return servings;
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
        return "Grocery{" + name + ", " + nutrition.toString() + ", " + servings + " servings}";
    }

    /**
     * checks if the object passed in equals to this grocery object
     * @param obj the object that this Grocery is compared to
     * @return returns true if the objects are the same
     */
    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof Grocery))
            return false;

        Grocery g = (Grocery) obj;

        return this.name.equalsIgnoreCase(g.name) &&
                this.nutrition.equals(g.nutrition) &&
                this.servings == g.servings;
    }
}
