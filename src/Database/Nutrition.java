package Database;

import java.io.Serializable;
import java.util.Objects;

public class Nutrition implements Serializable {
    private double calories, fat, sugar, fiber, protein, sodium;

    /**
     * Nutrition constructor creates a nutrition object
     * @param calories double containing the amount of calories
     * @param fat double containing the amount of fat
     * @param sugar double containing the amount of sugar
     * @param fiber double containing the amount of fiber
     * @param protein double containing the amount of protein
     * @param sodium double containing the amount of sodium
     */

    public Nutrition(double calories, double fat, double sugar, double fiber, double protein, double sodium) {
        if(calories < 0 || fat < 0 || sugar < 0 || fiber < 0 || protein < 0 || sodium < 0)
            throw new IllegalArgumentException("bad params in Nutrition");
        this.calories = calories;
        this.fat = fat;
        this.sugar = sugar;
        this.fiber = fiber;
        this.protein = protein;
        this.sodium = sodium;
    }

    /**
     * default constructor sets all the nutrition values to 0
     */
    public Nutrition(){
        this(0,0,0,0,0,0);
    }

    /**
     * returns the nutrition's calories
     * @return returns the calories field
     */
    public double getCalories(){
        return this.calories;
    }

    /**
     *
     * @return
     */
    public double getFat() {
        return fat;
    }

    /**
     *
     * @return
     */
    public double getFiber() {
        return fiber;
    }

    /**
     *
     * @return
     */
    public double getProtein() {
        return protein;
    }

    /**
     *
     * @return
     */
    public double getSodium() {
        return sodium;
    }

    /**
     *
     * @return
     */
    public double getSugar() {
        return sugar;
    }

//    public static Nutrition add(Nutrition a, Nutrition b) {
//        return new Nutrition(a.calories + b.calories,
//                a.fat + b.fat,
//                a.sugar + b.sugar,
//                a.fiber + b.fiber,
//                a.protein + b.protein,
//                a.sodium + b.sodium);
//    }

    /**
     *
     * @param a
     */
    public void add(Nutrition a) {
        this.calories += a.calories;
        this.fat += a.fat;
        this.sugar += a.sugar;
        this.fiber += a.fiber;
        this.protein += a.protein;
        this.sodium += a.sodium;
    }
//    public static Nutrition multiply(Nutrition a, double mul) {
//        return new Nutrition(a.calories * mul,
//                a.fat * mul,
//                a.sugar * mul,
//                a.fiber * mul,
//                a.protein * mul,
//                a.sodium * mul);
//    }

    /**
     *
     * @param mul
     * @return
     */
    public Nutrition multiply(double mul) {
        this.calories *= mul;
        this.fat *= mul;
        this.sugar *= mul;
        this.fiber *= mul;
        this.protein *= mul;
        this.sodium *= mul;
        return this;
    }
//    public static Nutrition divide(Nutrition a, double div) {
//        return Nutrition.multiply(a, 1.0/div);
//    }

    //public static Database.Nutrition getTotal(???<Database.Nutrition>);
    //public static Database.Nutrition getAverage(???<Database.Nutrition>);

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "Nutrition{Calories: " + calories
                + ", Fat: " + fat
                + ", Sugar: " + sugar
                + ", Fiber: " + fiber
                + ", Protein: " + protein
                + ", Sodium: " + sodium + "}";
    }

    /**
     *
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(calories, fat, sugar, fiber, protein, sodium);
    }

    /**
     *
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof Nutrition))
            return false;

        Nutrition n = (Nutrition) obj;
        if(Double.compare(this.getCalories(), n.getCalories()) != 0)
            return false;
        if(Double.compare(this.getFat(), n.getFat()) != 0)
            return false;
        if(Double.compare(this.getFiber(), n.getFiber()) != 0)
            return false;
        if(Double.compare(this.getProtein(), n.getProtein()) != 0)
            return false;
        if(Double.compare(this.getSodium(), n.getSodium()) != 0)
            return false;
        if(Double.compare(this.getSugar(), n.getSugar()) != 0)
            return false;
        return true;
    }
}
