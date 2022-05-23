package Factories;

import Database.Grocery;
import Database.Receipt;
import Database.SerializableDatabase;
import Search.FuzzySearch;
import Search.SearchResults;
import UI.UIHelpers;

import java.time.LocalDate;

public class ReceiptFactory {
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

            SearchResults<Grocery>.Result chosen = UIHelpers.chooseObject(results.getBestResults(), SearchResults.Result::toString);

            System.out.println(chosen.getObject());
            r.addGrocery(chosen.getObject(), groceryQuantity);
        } while(true);

        return r;
    }
}
