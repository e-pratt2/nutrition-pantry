package Factories;

import Database.Store;
import UI.UIHelpers;

public class StoreFactory {
    public Store createStore() {
        String str = UIHelpers.promptString("Input Store Name: ");
        return new Store(str);
    }
}
