package Factories;

import Database.Grocery;
import Database.Receipt;
import Database.SerializableDatabase;
import Search.FuzzySearch;
import Search.SearchResults;
import UI.ConsoleStyle;
import UI.UIHelpers;

import java.time.LocalDate;

/**
 * factory that provides the prompt to create a receipt object
 */
public class ReceiptFactory {

    /**
     * Prompt the user to create all the data needed for a receipt object.
     * @return the parsed object.
     */
    public Receipt createReceipt() {
        //Prompt for a date and create the base receipt
        LocalDate date = UIHelpers.promptDate("Receipt Date");
        Receipt r = new Receipt(date);

        //Loop as long as the user wishes to continue adding groceries
        do{
            Grocery chosenGrocery = null;

            //Loop as long as the user wants to keep searching
            while(true) {
                //Ask for a search phrase
                String groceryName = UIHelpers.promptString("Search for a grocery name: ");

                //Execute the search, collect the results object
                SearchResults<Grocery> results = FuzzySearch.search(groceryName, SerializableDatabase.getInstance().getGroceries(), Grocery::getName);

                //Prompt to choose from a list of the best 5, or search again
                SearchResults<Grocery>.Result chosen = UIHelpers.chooseObjectOrOther(
                        results.getBestResults(5), SearchResults.Result::toString,
                        "Search Again"
                );

                //If null, they chose to search again, fallthrough and repeat
                //Otherwise, assign the chosen grocery and break
                if(chosen != null) {
                    chosenGrocery = chosen.getObject();
                    break;
                }
            }

            //Prompt for the quantity,
            double groceryQuantity = UIHelpers.promptDouble("Quantity of " + ConsoleStyle.bold(chosenGrocery.getName()).green() + "? ");

            //Add it to the receipt!
            r.addGrocery(chosenGrocery, groceryQuantity);
        } while(UIHelpers.promptBoolean("Continue adding groceries to this receipt?", true));

        //Return completed receipt
        return r;
    }
}
