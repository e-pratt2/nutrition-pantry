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
        return null;//TODO::
    }
}
