import java.util.Objects;

public class Grocery {

    private String name;
    private Nutrition nutrition;

    public Grocery(String name, Nutrition nutrition) {
        this.name = name;
        this.nutrition = nutrition;
    }

    public Nutrition getNutrition() {
        return nutrition;
    }


    @Override
    public int hashCode() {
        return Objects.hash(name, nutrition);
    }
}
