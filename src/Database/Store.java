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
     *
     * @return returns the hashMap of grocery prices
     */
    public HashMap<Grocery, Double> getGroceryPrices(){return this.groceryPrices;}

    /**
     *
     * @return returns the arrayList of receipts
     */
    public ArrayList<Receipt> getR(){return this.receipts;}

    /**
     * returns the price of the grocery passed in or 0 if the grocery object is null
     * @param grocery Grocery object
     * @return returns the price of the grocery
     */
    public double getPriceOf(Grocery grocery){
        Double price = groceryPrices.get(grocery);
        return price == null ? 0.0 : price;
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
     * @return returns false if the objects are different or the object passed in is not a store object
     */
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
