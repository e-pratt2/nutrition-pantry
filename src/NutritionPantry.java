import Database.SerializableDatabase;
import Database.Store;
import Factories.*;
import Filters.FilterSet;
import UI.CommandLine;
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
        while(true)
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
                case 4:
                    CommandLine cl = new CommandLine();
                    FilterSet filters = cl.parseFilter();
                    System.out.println(filters);
                    cl.execute(filters);
                    break;
                case 5:
                    try {
                        if(SerializableDatabase.hasInstance()) {
                            if (UIHelpers.promptBoolean("A database is open. Overwrite?", false))
                                SerializableDatabase.loadInstance(UIHelpers.promptFilepath("Load path:").toString());
                        }
                        else SerializableDatabase.loadInstance(UIHelpers.promptFilepath("Load path:").toString());
                    } catch(IOException e) {
                        System.out.println("Failed to load database.");
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
        SerializableDatabase database = SerializableDatabase.getInstance();

        Store s = UIHelpers.chooseObject(database.getStores(), Store::getName);
        do {
            database.addReceipt(fact.createReceipt(), s);
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
