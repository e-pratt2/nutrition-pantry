package Database;

import java.io.Serializable;
import java.util.HashMap;
import java.time.LocalDate;
import java.util.Set;


/**
 * a database class that holds a list of groceries
 */
public class Receipt implements Serializable {
    public static final long serialVersionUID = 1L;

    private final HashMap<Grocery, Double> groceryQuantity;
    private final LocalDate date;

    /**
     * constructor that creates a Receipt object initialises all the private fields
     * @param date Local date variable
     * @throws IllegalArgumentException if date is null
     */
    public Receipt(LocalDate date) {
        if(date == null)
            throw new IllegalArgumentException("bad params in receipt");
        this.date = date;
        this.groceryQuantity = new HashMap<>();
    }

    /**
     * this method returns the quantity of the object passed in
     * @param grocery Grocery object to find the quantity of
     * @return returns the quantity of the object or if null returns 0
     */
    public double getQuantityOf(Grocery grocery) {
        Double quantity = this.groceryQuantity.get(grocery);
        return quantity == null ? 0.0 : quantity;
    }

    /**
     *
     * @return returns the date of this receipt
     */
    public LocalDate getDate() {
        return this.date;
    }

    /**
     * adds a grocery and quantity of the grocery into the hash map
     * @param g Grocery object added
     * @param quantity the quantity of the grocery
     */
    public void addGrocery(Grocery g, double quantity) {
        this.groceryQuantity.put(g, this.getQuantityOf(g) + quantity);
    }

    /**
     *
     * @return returns the key-set of the hash map
     */
    public Set<Grocery> getGroceries() {
        return groceryQuantity.keySet();
    }
}
