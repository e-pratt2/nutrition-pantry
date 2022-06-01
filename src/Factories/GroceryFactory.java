package Factories;

import Database.Grocery;

/**
 * abstract interface
 */
public interface GroceryFactory {
    /**
     * abstract method
     * @return returns a grocery object
     */
    public Grocery createGrocery();
}
