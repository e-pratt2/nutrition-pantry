package Factories;

import Database.Grocery;
import Database.Nutrition;
import UI.UIHelpers;

/**
 * a factory class that creates a grocery object
 * (allows a user to create a grocery object manually
 * */
public class DIYFactory implements GroceryFactory{

    /**
     * overrides the create grocery method in the GroceryFactory interface
     * @return the new grocery object created
     */
    @Override
    public Grocery createGrocery(){
        String name = promptName();
        Nutrition n = getNutrition();
        double servings = UIHelpers.promptDouble("Servings per container: ");

        return new Grocery(name, n, servings);
    }

    /**
     * prompts the used for the name of the grocery
     * @return returns the sting with the grocery name
     */
    private String promptName(){
        return UIHelpers.promptString("Grocery name: ");
    }

    /**
     * prompts the used for the nutrition information of the grocery
     * @return returns the nutrition object associated with the grocery
     */
    private Nutrition getNutrition(){

        double cal = UIHelpers.promptDouble("Amount of Calories: ");
        double fat = UIHelpers.promptDouble("Amount of Fat: ");
        double sugar = UIHelpers.promptDouble("Amount of Sugar: ");
        double fib = UIHelpers.promptDouble("Amount of Fiber: ");
        double pro = UIHelpers.promptDouble("Amount of Protein: ");
        double sod = UIHelpers.promptDouble("Amount of Sodium: ");

        Nutrition n = new Nutrition(cal, fat, sugar, fib, pro, sod);
        return n;
    }
}
