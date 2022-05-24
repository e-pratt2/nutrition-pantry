package Database;

import java.io.Serializable;
import java.util.Objects;

public class Grocery implements Serializable {

    private String name;
    private Nutrition nutrition;

    public Grocery(String name, Nutrition nutrition) {
        if(name == null || name.equalsIgnoreCase("") || nutrition == null)
            throw new IllegalArgumentException("Bad param in Grocery");
        this.name = name;
        this.nutrition = nutrition;
    }

    public Nutrition getNutrition() {
        return nutrition;
    }

    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, nutrition);
    }

    @Override
    public String toString() {
        return "Grocery{" + name + ", " + nutrition.toString() + "}";
    }

    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof Grocery))
            throw new IllegalArgumentException("bad param in equals");

        Grocery g = (Grocery) obj;

        if(this.getName().equalsIgnoreCase(g.getName()) && this.getNutrition().equals(g.getNutrition()))
            return true;
        return false;
    }
}
