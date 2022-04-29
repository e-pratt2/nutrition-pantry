import java.util.HashMap;
import java.time.LocalDate;

public class Receipt {

    private HashMap<Grocery, Double> groceryQuantity;
    private LocalDate date;

    public Receipt(LocalDate date) {
        this.date = date;
        groceryQuantity = new HashMap<>();
    }

    public double getQuantityOf(Grocery grocery) {
        Double quantity = groceryQuantity.get(grocery);
        return quantity == null ? 0.0 : quantity;
    }

    public LocalDate getDate() {
        return date;
    }

    public void addGrocery(Grocery g, double quantity) {
        groceryQuantity.put(g, getQuantityOf(g) + quantity);
    }
}
