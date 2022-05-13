package Database;

import Filters.Filter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Store implements Serializable {
    private HashMap<Grocery, Double> groceryPrices;
    private ArrayList<Receipt> receipts;
    private String name;

    public Store(String name){
        this.groceryPrices = new HashMap<>();
        this.receipts = new ArrayList<>();
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public double getPriceOf(Grocery grocery){
        Double price = groceryPrices.get(grocery);
        return price == null ? 0.0 : price;
    }

    public void addReceipt(Receipt receipt){
        this.receipts.add(receipt);
    }
    public List<Receipt> getReceipts() {
        return this.receipts;
    }
}
