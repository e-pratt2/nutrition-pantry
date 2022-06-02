package Database;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * a database class that creates a store object and holds a list of receipts and grocery prices
 */
public class Store implements Serializable {

    private final HashMap<Grocery, Double> groceryPrices;
    private final ArrayList<Receipt> receipts;
    private final String name;

    /**
     * constructor creates a Store object
     * @param name string that has the name of the store
     * @throws IllegalArgumentException if the name is null or empty
     */
    public Store(String name){
        if(name == null || name.equalsIgnoreCase(""))
            throw new IllegalArgumentException("bad param in Store");
        this.groceryPrices = new HashMap<>();
        this.receipts = new ArrayList<>();
        this.name = name;
    }

    /**
     *
     * @return returns the name of this store
     */
    public String getName() {
        return this.name;
    }

    /**
     * returns the price of the grocery passed in or 0 if the grocery object is null
     * @param grocery Grocery object
     * @return returns the price of the grocery
     */
    public double getPriceOf(Grocery grocery){
        Double price = groceryPrices.get(grocery);
        return price == null ? 0.0 : price;
    }
    public void setPriceOf(Grocery g, double price) {
        groceryPrices.put(g, price);
    }

    public boolean hasPrice(Grocery grocery) {
        return groceryPrices.containsKey(grocery);
    }

    /**
     * adds the receipt passed in, into the arrayList of receipts
     * @param receipt receipt object to be added
     */
    public void addReceipt(Receipt receipt){
        this.receipts.add(receipt);
    }

    /**
     *
     * @return returns the List of receipts
     */
    public List<Receipt> getReceipts() {
        return this.receipts;
    }

    /**
     * checks if the object passed in equals this object
     * @param obj object this store object is compared to
     * @return returns true if the objects are equal
     */
    public boolean equals(Object obj){

        if(!(obj instanceof Store))
            return false;

        Store s = (Store) obj;

        if(!this.name.equals(s.name))
            return false;
        if(!this.groceryPrices.equals(s.groceryPrices))
            return false;
        if(!this.receipts.equals(s.receipts))
            return false;
        return true;
    }
}
