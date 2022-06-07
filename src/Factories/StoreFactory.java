package Factories;

import Database.Store;
import UI.UIHelpers;

/**
 * store factory that creates a store object
 */
public class StoreFactory {

    /**
     * creates a store object
     * @return the store object created
     */
    public Store createStore() {
        //prompts the user for the name of the store
        String str = UIHelpers.promptString("Input Store Name: ");
        //returns the store object
        return new Store(str);
    }
}
