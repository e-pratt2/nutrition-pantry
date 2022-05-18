import Database.Grocery;
import Database.SerializableDatabase;
import Database.Store;
import Factories.*;
import Filters.AlwaysPassFilter;
import Filters.Filter;
import UI.UIHelpers;

import java.io.IOException;
import java.util.Scanner;

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
            case 3:
                addGroceries();
                break;
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
        Store s = null;
        while(true) {
            String name = UIHelpers.promptString("Store name:");
            s = SerializableDatabase.getInstance().findStore(name);
            if(s == null)
                System.out.println("Store not found, try again.");
            else
                break;
        }
        do {
            SerializableDatabase.getInstance().addReceipt(fact.createReceipt(), s);
        } while(UIHelpers.promptBoolean("Continue?", true));
    }

    private static void addGroceries(){
        String str = UIHelpers.promptString("Do you want to add the grocery manually or using a UPC? (DIY/UPC)");
        GroceryFactory groceryFactory;
        if(str.equalsIgnoreCase("DIY"))
            groceryFactory = new DIYFactory();
        else if(str.equalsIgnoreCase("UPC"))
            groceryFactory = new UPCGroceryFactory();
        else{
            System.out.println("Invalid Choice! Returning to Main Menu.");
            return;
        }

        do{
            SerializableDatabase.getInstance().addGrocery(groceryFactory.createGrocery());
        }while(UIHelpers.promptBoolean("Continue?", true));
    }
}
