package Database;

import java.util.ArrayList;
import java.util.HashMap;

public class Store {
    private HashMap<Grocery, Double> groceryPrices;
    private ArrayList<Receipt> receipts;
    private String name;

    public Store(String name){
        this.groceryPrices = new HashMap<>();
        this.receipts = new ArrayList<>();
        this.name = name;
    }

    public double getPriceOf(Grocery grocery){
        Double price = groceryPrices.get(grocery);
        return price == null ? 0.0 : price;
    }

    public void addR(Receipt receipt){
        this.receipts.add(receipt);
    }
}
