package Factories;

import Database.Receipt;
import UI.UIHelpers;

public class ReceiptFactory {
    public Receipt createReceipt() {
        //Receipt r = new Receipt();
        String groceryName;
        do{
            groceryName = UIHelpers.promptString("Grocery name: ");
            if(groceryName.isBlank())
                break;
            double groceryQuantity = UIHelpers.promptDouble("Quantity: ");

            //r.addGrocery();
        } while(true);

        return null;
    }
}
