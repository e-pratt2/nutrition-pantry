package Factories;

import Database.Grocery;
import Database.Nutrition;

public class UPCGroceryFactory implements GroceryFactory {
    @Override
    public Grocery createGrocery() {
        return null;
    }
    private String promptUPC() {
        return null;
    }
    private boolean validateUPC(String upc) {
        return false;
    }
    private String getOpenFoodFactsJSON(String upc) {
        return null;
    }
    private Nutrition parseJSON(String json) {
        return null;
    }
}
