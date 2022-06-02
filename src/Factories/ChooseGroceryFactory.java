package Factories;

import Database.Grocery;
import UI.ConsoleStyle;
import UI.UIHelpers;

public class ChooseGroceryFactory implements GroceryFactory {
    @Override
    public Grocery createGrocery() {
        String str = UIHelpers.promptString("Do you want to add the grocery manually or using a UPC? " + ConsoleStyle.bold("[DIY/UPC] ").blue());
        GroceryFactory groceryFactory;
        while(true) {
            if (str.equalsIgnoreCase("DIY")) {
                groceryFactory = new DIYFactory();
                break;
            } else if (str.equalsIgnoreCase("UPC")) {
                groceryFactory = new UPCGroceryFactory();
                break;
            } else {
                UIHelpers.promptString("Invalid input, please try again: ");
            }
        }

        return groceryFactory.createGrocery();
    }
}
