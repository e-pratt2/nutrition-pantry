package Factories;

import Database.Grocery;
import Database.Receipt;
import Database.SerializableDatabase;
import Search.FuzzySearch;
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

            Grocery g = FuzzySearch.findBestMatch(groceryName, SerializableDatabase.getInstance().getGroceries(), Grocery::getName);

            System.out.println(g);
            r.addGrocery(g, groceryQuantity);
        } while(true);

        return r;
    }
}
