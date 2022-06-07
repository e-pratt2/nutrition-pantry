package Factories;

import Database.Grocery;
import UI.ConsoleStyle;
import UI.UIHelpers;

public class ChooseGroceryFactory implements GroceryFactory {
    @Override
    public Grocery createGrocery() {
        //Prompt for which kind of grocery input method they would like
        String str = UIHelpers.promptString("Do you want to add the grocery manually or using a UPC? " + ConsoleStyle.bold("[DIY/UPC] ").blue());

        GroceryFactory groceryFactory;
        //Loop until valid input
        while(true) {
            if (str.equalsIgnoreCase("DIY")) {
                //Create a DIY-type factory
                groceryFactory = new DIYFactory();
                break;
            } else if (str.equalsIgnoreCase("UPC")) {
                //Create a UPC-type factory
                groceryFactory = new UPCGroceryFactory();
                break;
            } else {
                //Reprompt for a valid factory type
                UIHelpers.promptString("Invalid input, please try again: ");
            }
        }

        //Execute the chosen factory.
        return groceryFactory.createGrocery();
    }
}
