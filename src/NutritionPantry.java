import Database.Grocery;
import Database.Receipt;
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

/**
 * Holds the entry point and helper methods for allowing easy interaction with the database.
 */
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

    /**
     * Main entry point for the program. Provides a text-based prompt for the user to interact with the database,
     * add items, perform analysis, and save/load.
     * @param args - Unused, leave empty.
     */
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
                                SerializableDatabase.loadInstance(chooseFile().toString());
                            else
                                System.out.println("Load cancelled.");
                        }
                        else SerializableDatabase.loadInstance(chooseFile().toString());
                    } catch(IOException e) {
                        System.out.println("Failed to load database.");
                    }
                    break;
                case 6:
                    try {
                        File file = chooseFile();

                        if(file.exists()) {
                            if (UIHelpers.promptBoolean("File exists. Overwrite?", false))
                                SerializableDatabase.saveInstance(file.toString());
                            else {
                                System.out.println("Save cancelled.");
                                break;
                            }
                        }

                    } catch(IOException e) {
                        System.out.println("Failed to save database.");
                    }
                    break;
                case 7:
                    try {
                        File file = chooseFile();

                        if(file.exists()) {
                            if (UIHelpers.promptBoolean("File exists. Overwrite?", false))
                                SerializableDatabase.saveInstance(file.toString());
                            else {
                                System.out.println("Save cancelled.");
                                break;
                            }
                        }
                        return;
                    } catch(IOException e) {
                        System.out.println("Failed to save database, refusing to exit.");
                        break;
                    }
            }
    }

    /**
     * Prompts the user to add any number of receipts to the database. Continues prompting until the user is done.
     * Requires that there be stores present in the database beforehand.
     */
    private static void addReceipts() {
        ReceiptFactory fact = new ReceiptFactory();
        SerializableDatabase database = SerializableDatabase.getInstance();

        Store s = UIHelpers.chooseObject(database.getStores(), Store::getName);
        if(s == null) {
            System.out.println(ConsoleStyle.red("Please add some stores first!"));
            return;
        }
        do {
            Receipt r = fact.createReceipt();

            //Find any groceries without an associated price, and prompt for their price.
            for(Grocery g : r.getGroceries()) {
                if(!s.hasPrice(g)) {
                    double price = UIHelpers.promptDouble("Price of " + ConsoleStyle.bold(g.getName()).green()
                                    + " at " + ConsoleStyle.bold(s.getName()).green() + "? ");

                    s.setPriceOf(g, price);
                }
            }

            database.addReceipt(r, s);
        } while(UIHelpers.promptBoolean("Continue adding receipts to " + ConsoleStyle.green(s.getName()) + "?", true));
    }

    /**
     * Prompts the user to enter any number of groceries. Will first prompt entry style (UPC or DIY), and then
     * continuously prompt for new groceries in that style until the user is done.
     */
    private static void addGroceries(){
        String str = UIHelpers.promptString("Do you want to add the grocery manually or using a UPC? " + ConsoleStyle.bold("[DIY/UPC] ").blue());
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
        }while(UIHelpers.promptBoolean("Continue adding groceries?", true));
    }

    /**
     * Prompts the user to choose a file to save/load to/from. Provides a list of files in the working directory to
     * choose from, or allows the user to specify their own path.
     * @return File - the chosen file object, which may or may not exist already.
     */
    private static File chooseFile() {
        File folder = new File(".");
        File[] items = folder.listFiles();

        List<File> files = Arrays.stream(items).filter(f -> f.isFile()).collect(Collectors.toList());

        File chosen = UIHelpers.chooseObjectOrOther(files, File::getName, "Other...");

        if(chosen != null)
            return chosen;

        do {
            Path path = UIHelpers.promptFilepath("Enter path");

            return path.toFile();
        } while(true);
    }
}
