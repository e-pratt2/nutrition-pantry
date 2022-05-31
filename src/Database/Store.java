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
        if(name == null || name.equalsIgnoreCase(""))
            throw new IllegalArgumentException("bad param in Store");
        this.groceryPrices = new HashMap<>();
        this.receipts = new ArrayList<>();
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
    public HashMap<Grocery, Double> getGroceryPrices(){return this.groceryPrices;}
    public ArrayList<Receipt> getR(){return this.receipts;}

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

    public boolean equals(Object obj){

        if(!(obj instanceof Store))
            return false;

        Store s = (Store) obj;

        if(!this.groceryPrices.equals(s.getGroceryPrices()))
            return false;
        if(!this.name.equals(s.getName()))
            return false;
        if(!this.receipts.equals(s.getR()))
            return false;
        return true;
    }
}
