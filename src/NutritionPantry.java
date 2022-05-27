import Database.SerializableDatabase;
import Database.Store;
import Factories.*;
import Filters.FilterSet;
import UI.CommandLine;
import UI.CommandSyntaxException;
import UI.ConsoleStyle;
import UI.UIHelpers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
            switch(UIHelpers.promptMenu(menuOptions)) {
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

                    while(true) {
                        try{
                            if(!cl.fetchAndExecute())
                                break;
                        } catch (CommandSyntaxException e) {
                            System.out.println("" + ConsoleStyle.bold("Command error: ").red() + e.getMessage());
                        }
                    }

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
                    break;
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
        if(s == null) {
            System.out.println("Please add some stores first!");
            return;
        }
        do {
            database.addReceipt(fact.createReceipt(), s);
        } while(UIHelpers.promptBoolean("Continue?", true));
    }

    private static void addGroceries(){
        String str = UIHelpers.promptString("Do you want to add the grocery manually or using a UPC? " + ConsoleStyle.bold("[DIY/UPC]").blue());
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
    private static File chooseFile() {
        File folder = new File(".");
        File[] items = folder.listFiles();

        List<File> files = Arrays.stream(items).filter(f -> f.isFile()).collect(Collectors.toList());

        File chosen = UIHelpers.chooseObjectOrOther(files, File::getName, "Other...");

        if(chosen != null)
            return chosen;

        do {
            Path path = UIHelpers.promptFilepath("Enter path");

            return null; //todo
        } while(true);
    }
}
