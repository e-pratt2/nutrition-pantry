package Factories;

import Database.Grocery;
import Database.Nutrition;
import UI.UIHelpers;

public class DIYFactory implements GroceryFactory{

    @Override
    public Grocery createGrocery(){
        return null;//TODO::
    }

    private String promptName(){
        return null;
    }

    private Nutrition getNutrition(){
        double cal = UIHelpers.promptDouble();
        double fat = UIHelpers.promptDouble();
        double sugar = UIHelpers.promptDouble();
        double fib = UIHelpers.promptDouble();
        double pro = UIHelpers.promptDouble();
        double sod = UIHelpers.promptDouble();

        Nutrition n = new Nutrition(cal, fat, sugar, fib, pro, sod);
        return n;
    }
}
