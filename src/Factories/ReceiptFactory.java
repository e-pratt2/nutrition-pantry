package Factories;

import Database.Grocery;
import Database.Receipt;
import Database.SerializableDatabase;
import Search.FuzzySearch;
import Search.SearchResults;
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
            groceryName = UIHelpers.promptString("Grocery name: ");
            if(groceryName.isBlank())
                break;
            double groceryQuantity = UIHelpers.promptDouble("Quantity: ");

            SearchResults<Grocery> results = FuzzySearch.search(groceryName, SerializableDatabase.getInstance().getGroceries(), Grocery::getName);

            SearchResults<Grocery>.Result chosen = UIHelpers.chooseObjectOrOther(
                    results.getBestResults(5), SearchResults.Result::toString,
                    "Create new"
            );

            System.out.println(chosen.getObject());
            r.addGrocery(chosen.getObject(), groceryQuantity);
        } while(UIHelpers.promptBoolean("Continue? ", true));

        return r;
    }
}
