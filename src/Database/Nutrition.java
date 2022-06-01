package Database;

import java.io.Serializable;
import java.util.Objects;

/**
 * A database class that creates a nutrition object that holds the nutrition information of the grocery object
 */
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
     * @return returns the nutrition's fat
     */
    public double getFat() {
        return fat;
    }

    /**
     *
     * @return returns the nutrition's fiber
     */
    public double getFiber() {
        return fiber;
    }

    /**
     *
     * @return returns the nutrition's protein
     */
    public double getProtein() {
        return protein;
    }

    /**
     *
     * @return returns the nutrition's sodium
     */
    public double getSodium() {
        return sodium;
    }

    /**
     *
     * @return returns the nutrition's sugar
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
     * adds one nutrition object to another
     * @param a the Nutrition object added
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
     * multiplies this Nutrition object
     * @param mul the number the nutrition information is added by
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
     * @return returns Nutrition{Calories: ,Fat: ,Sugar: ,Fiber: ,Protein: ,Sodium: }
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
     * this is the hashcode method
     * @return returns the hashcode of all the private fields
     */
    @Override
    public int hashCode() {
        return Objects.hash(calories, fat, sugar, fiber, protein, sodium);
    }

    /**
     * checks if the object passed in equals this Nutrition object
     * @param obj the object this nutrition object is compared to
     * @return returns true if the objects are the same
     * @return return false if the object is not a Nutrition object or if the Nutrition objects are different
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
