import Database.Grocery;
import Database.SerializableDatabase;
import Database.Store;
import Factories.DIYFactory;
import Factories.StoreFactory;
import Factories.ReceiptFactory;
import Factories.UPCGroceryFactory;
import Filters.AlwaysPassFilter;
import Filters.Filter;
import UI.UIHelpers;

import java.io.IOException;

public class NutritionPantry {
    public static final String menuOptions[] = {
            "Add store...",
            "Add receipts to store...",
            "Add groceries...",
            "Analysis...",
            "Load from file...",
            "Save to file...",
            "Save and exit."
    };
    public static void main(String[] args) {
        switch(UIHelpers.menu(menuOptions)) {
            case 1:
                StoreFactory s = new StoreFactory();
                SerializableDatabase.getInstance().addStore(s.createStore());
                break;
            case 2:
                addReceipts();
                break;
            case 2: break;
            case 3: break;
            case 4: break;
            case 5:
                try {
                    if(SerializableDatabase.hasInstance()) {
                        if (UIHelpers.promptBoolean("A database is open. Overwrite?", false))
                            SerializableDatabase.loadInstance(UIHelpers.promptFilepath("Load path:").toString());
                    }
                    else SerializableDatabase.loadInstance(UIHelpers.promptFilepath("Load path:").toString());
                } catch(IOException e) {
                    System.out.println("Failed to save database.");
                }
            case 6:
                try {
                    SerializableDatabase.saveInstance(UIHelpers.promptFilepath("Save path:").toString());
                } catch(IOException e) {
                    System.out.println("Failed to save database.");
                }
                break;
            case 7:
                try {
                    SerializableDatabase.saveInstance(UIHelpers.promptFilepath("Save path:").toString());
                    return;
                } catch(IOException e) {
                    System.out.println("Failed to save database, refusing to exit.");
                    break;
                }
        }
    }
    private static void addReceipts() {
        ReceiptFactory fact = new ReceiptFactory();
        Store s = SerializableDatabase.getInstance().findStore(UIHelpers.promptString("Store name:"));
        do {
            SerializableDatabase.getInstance().addReceipt(fact);
        } while(UIHelpers.promptBoolean("Continue?", true));
    }
}
