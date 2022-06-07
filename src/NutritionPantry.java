import Database.Grocery;
import Database.Receipt;
import Database.SerializableDatabase;
import Database.Store;
import Factories.*;
import UI.CommandLine;
import UI.CommandSyntaxException;
import UI.ConsoleStyle;
import UI.UIHelpers;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Holds the entry point and helper methods for allowing easy interaction with the database.
 */
public class NutritionPantry {
    public static final String[] menuOptions = {
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
        //At the start, prompt the user to open or create a database.
        System.out.println(ConsoleStyle.bold("Choose a database to open!").green());

        File open = chooseFileOrNone();

        //Not null - a file was chosen
        if(open != null) {
            try {
                //Try to open, if it fails fallback to creating a new one
                SerializableDatabase.loadInstance(open.getPath());
            } catch(IOException e) {
                System.out.println(ConsoleStyle.bold("Could not open database:").red() + e.getMessage());
                System.out.println("Creating new database.");
            }
        } else {
            System.out.println("Creating new database.");
        }

        //Enter the main loop of the program, asking for actions to perform
        while(true)
            //Try to execute a user command, scald them appropriately if they cause an error
            try {
                //Switch on the user's choice from the menu....
                switch (UIHelpers.promptMenu(menuOptions)) {
                    case 1: //Add store
                        StoreFactory s = new StoreFactory();
                        SerializableDatabase.getInstance().addStore(s.createStore());
                        break;
                    case 2: //Add receipts
                        addReceipts();
                        break;
                    case 3: //Add Groceries
                        addGroceries();
                        break;
                    case 4: //Analysis...
                        CommandLine cl = new CommandLine();

                        //Continue to execute as long as the command line says to
                        while (true) {
                            //Attempts to parse and execute. Will catch syntax errors, but allows
                            //internal errors to propagate out.
                            try {
                                if (!cl.fetchAndExecute())
                                    break;
                            } catch (CommandSyntaxException e) {
                                System.out.println(ConsoleStyle.bold("Command error: ").red() + e.getMessage());
                            }
                        }

                        break;
                    case 5:
                        try {
                            if (SerializableDatabase.hasInstance()) {
                                if (UIHelpers.promptBoolean("A database is open. Overwrite?", false))
                                    SerializableDatabase.loadInstance(chooseFile().toString());
                                else
                                    System.out.println("Load cancelled.");
                            } else SerializableDatabase.loadInstance(chooseFile().toString());
                        } catch (IOException e) {
                            System.out.println(ConsoleStyle.bold("Failed to open database:").red() + e.getMessage());
                        }
                        break;
                    case 6:
                        try {
                            File file = chooseFile();

                            if (file.exists()) {
                                if (UIHelpers.promptBoolean("File exists. Overwrite?", false))
                                    SerializableDatabase.saveInstance(file.toString());
                                else {
                                    System.out.println("Save cancelled.");
                                    break;
                                }
                            } else {
                                SerializableDatabase.saveInstance(file.toString());
                            }

                        } catch (IOException e) {
                            System.out.println(ConsoleStyle.bold("Failed to save database:").red() + e.getMessage());
                        }
                        break;
                    case 7:
                        try {
                            File file = chooseFile();

                            if (file.exists()) {
                                if (UIHelpers.promptBoolean("File exists. Overwrite?", false))
                                    SerializableDatabase.saveInstance(file.toString());
                                else {
                                    System.out.println("Save cancelled.");
                                    break;
                                }
                            } else {
                                SerializableDatabase.saveInstance(file.toString());
                            }
                            return;
                        } catch (IOException e) {
                            System.out.println(ConsoleStyle.bold("Failed to save database:").red() + e.getMessage());
                            System.out.println("Refusing to exit.");
                            break;
                        }
                }
            } catch(RuntimeException e) {
                System.out.println(ConsoleStyle.bold("Internal error: ").red() + e.getMessage() + " - don't do that!");
            }
    }

    /**
     * Prompts the user to add any number of receipts to the database. Continues prompting until the user is done.
     * Requires that there be stores present in the database beforehand.
     */
    private static void addReceipts() {
        //Create a receipt factory
        ReceiptFactory fact = new ReceiptFactory();
        SerializableDatabase database = SerializableDatabase.getInstance();

        //Choose which store to add to...
        Store s = UIHelpers.chooseObject(database.getStores(), Store::getName);
        if(s == null) {
            //No stores, show an error and exit
            System.out.println(ConsoleStyle.red("Please add some stores first!"));
            return;
        }

        //Enter a loop - keep creating groceries until the user is done
        do {
            //Prompt and create the receipt
            Receipt r = fact.createReceipt();

            //Find any groceries without an associated price, and prompt for their price.
            for(Grocery g : r.getGroceries()) {
                if(!s.hasPrice(g)) {
                    //Prompt for price,
                    double price = UIHelpers.promptDouble("Price of " + ConsoleStyle.bold(g.getName()).green()
                                    + " at " + ConsoleStyle.bold(s.getName()).green() + "? ");

                    //Set the price within the store object
                    s.setPriceOf(g, price);
                }
            }

            //Add this receipt to the database
            database.addReceipt(r, s);

            //Prompt if continue (default to true)
        } while(UIHelpers.promptBoolean("Continue adding receipts to " + ConsoleStyle.green(s.getName()) + "?", true));
    }

    /**
     * Prompts the user to enter any number of groceries. Will first prompt entry style (UPC or DIY), and then
     * continuously prompt for new groceries in that style until the user is done.
     */
    private static void addGroceries(){
        //Make the factory that allows the user to choose input type
        GroceryFactory factory = new ChooseGroceryFactory();

        //Continuously ask for new groceries until the user exits
        do{
            SerializableDatabase.getInstance().addGrocery(factory.createGrocery());
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

        //If there are items in the folder...
        if(items != null) {
            List<File> files = Arrays.stream(items).filter(File::isFile).collect(Collectors.toList());

            File chosen = UIHelpers.chooseObjectOrOther(files, File::getName, "Other...");

            if (chosen != null)
                return chosen;

            //If chosen was null, fallthrough and prompt
        }

        //Prompt the user for a file if none was specified by the menu
        return UIHelpers.promptFilepath("Enter path: ").toFile();
    }

    /**
     * Prompts the user to choose a file to save/load to/from. Provides a list of files in the working directory to
     * choose from, or allows the user to specify "none"
     * @return File - the chosen file object, which may or may not exist already.
     */
    private static File chooseFileOrNone() {
        File folder = new File(".");
        File[] items = folder.listFiles();

        //If there are items in the folder...
        if(items != null) {
            List<File> files = Arrays.stream(items).filter(File::isFile).collect(Collectors.toList());

            File chosen = UIHelpers.chooseObjectOrOther(files, File::getName, "Create new...");

            return chosen;

            //If chosen was null, fallthrough and prompt
        }

        return null;
    }
}
