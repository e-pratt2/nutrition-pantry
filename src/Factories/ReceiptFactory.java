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
        LocalDate date = UIHelpers.promptDate("Receipt Date");
        Receipt r = new Receipt(date);
        String groceryName;

        do{
            Grocery chosenGrocery = null;

            while(true) {
                groceryName = UIHelpers.promptString("Search for a grocery name: ");
                if (groceryName.isBlank())
                    break;


                SearchResults<Grocery> results = FuzzySearch.search(groceryName, SerializableDatabase.getInstance().getGroceries(), Grocery::getName);

                SearchResults<Grocery>.Result chosen = UIHelpers.chooseObjectOrOther(
                        results.getBestResults(5), SearchResults.Result::toString,
                        "Search Again"
                );

                //If null, they chose to search again
                if(chosen != null) {
                    chosenGrocery = chosen.getObject();
                    break;
                }
            }

            double groceryQuantity = UIHelpers.promptDouble("Quantity of " + ConsoleStyle.bold(chosenGrocery.getName()).green() + "? ");

            r.addGrocery(chosenGrocery, groceryQuantity);
        } while(UIHelpers.promptBoolean("Continue adding groceries to this receipt?", true));

        return r;
    }
}
