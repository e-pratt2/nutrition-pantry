package Database;

import java.io.Serializable;
import java.util.Objects;

public class Grocery implements Serializable {

    private String name;
    private Nutrition nutrition;

    public Grocery(String name, Nutrition nutrition) {
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
}
