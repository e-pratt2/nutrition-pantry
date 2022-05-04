package Factories;

import Database.Grocery;
import Database.Nutrition;
import UI.UIHelpers;

public class DIYFactory implements GroceryFactory{

    @Override
    public Grocery createGrocery(){
        String name = promptName();
        Nutrition n = getNutrition();

        return new Grocery(name, n);
    }

    private String promptName(){
        return UIHelpers.promptString("Grocery name: \n");
    }

    private Nutrition getNutrition(){

        double cal = UIHelpers.promptDouble("Amount of Calories: ");
        double fat = UIHelpers.promptDouble("Amount of Fat: ");
        double sugar = UIHelpers.promptDouble("Amount of Sugar: ");
        double fib = UIHelpers.promptDouble("Amount of Fiber: ");
        double pro = UIHelpers.promptDouble("Amount of Protein: ");
        double sod = UIHelpers.promptDouble(" Amount of Sodium: ");

        Nutrition n = new Nutrition(cal, fat, sugar, fib, pro, sod);
        return n;
    }
}
