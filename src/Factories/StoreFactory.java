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
        String str = UIHelpers.promptString("Input Store Name: ");
        return new Store(str);
    }
}
