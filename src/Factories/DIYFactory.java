package Factories;

import Database.Grocery;
import Database.Nutrition;

public class DIYFactory implements GroceryFactory{

    @Override
    public Grocery createGrocery(){
        return null;//TODO::
    }

    private String promptName(){
        return "";
    }

    private Nutrition getNutrition(){
        return null;//TODO::
    }

    private double promt(String string){
        return 0.0;//TODO::
    }
}
