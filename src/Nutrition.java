import java.util.Objects;

public class Nutrition {
    private double calories, fat, sugar, fiber, protein, sodium;

    public double getCalories(double calories){
        this.calories = calories;
    }

    @Override
    public int hashCode() {
        return Objects.hash(calories, fat, sugar, fiber, protein, sodium);
    }
}
