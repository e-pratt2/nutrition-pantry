import java.util.Objects;

public class Nutrition {
    private double calories, fat, sugar, fiber, protein, sodium;

    public Nutrition(double calories, double fat, double sugar, double fiber, double protein, double sodium) {
        this.calories = calories;
        this.fat = fat;
        this.sugar = sugar;
        this.fiber = fiber;
        this.protein = protein;
        this.sodium = sodium;
    }

    public double getCalories(){
        return this.calories;
    }

    public double getFat() {
        return fat;
    }

    public double getFiber() {
        return fiber;
    }

    public double getProtein() {
        return protein;
    }

    public double getSodium() {
        return sodium;
    }

    public double getSugar() {
        return sugar;
    }

    @Override
    public int hashCode() {
        return Objects.hash(calories, fat, sugar, fiber, protein, sodium);
    }
}
