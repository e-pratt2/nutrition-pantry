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
    public Nutrition(){
        this(0,0,0,0,0,0);
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

    public static Nutrition add(Nutrition a, Nutrition b) {
        return new Nutrition(a.calories + b.calories,
                a.fat + b.fat,
                a.sugar + b.sugar,
                a.fiber + b.fiber,
                a.protein + b.protein,
                a.sodium + b.sodium);
    }
    public void add(Nutrition a) {
        this.calories += a.calories;
        this.fat += a.fat;
        this.sugar += a.sugar;
        this.fiber += a.fiber;
        this.protein += a.protein;
        this.sodium += a.sodium;
    }
    public static Nutrition multiply(Nutrition a, double mul) {
        return new Nutrition(a.calories * mul,
                a.fat * mul,
                a.sugar * mul,
                a.fiber * mul,
                a.protein * mul,
                a.sodium * mul);
    }
    public Nutrition multiply(double mul) {
        this.calories *= mul;
        this.fat *= mul;
        this.sugar *= mul;
        this.fiber *= mul;
        this.protein *= mul;
        this.sodium *= mul;
        return this;
    }
    public static Nutrition divide(Nutrition a, double div) {
        return Nutrition.multiply(a, 1.0/div);
    }
    //TODO:
    //public static Nutrition getTotal(???<Nutrition>);
    //public static Nutrition getAverage(???<Nutrition>);

    @Override
    public int hashCode() {
        return Objects.hash(calories, fat, sugar, fiber, protein, sodium);
    }
}
