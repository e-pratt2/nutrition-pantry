import java.util.HashMap;
import java.time.LocalDate;

public class Receipt {

    private HashMap<Grocery, Double> groceryQuantity;
    private LocalDate date;

    public Receipt(LocalDate date) {
        this.date = date;
        this.groceryQuantity = new HashMap<>();
    }

    public double getQuantityOf(Grocery grocery) {
        Double quantity = this.groceryQuantity.get(grocery);
        return quantity == null ? 0.0 : quantity;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public void addGrocery(Grocery g, double quantity) {
        this.groceryQuantity.put(g, this.getQuantityOf(g) + quantity);
    }

    public Nutrition getTotalNutrition() {
        Nutrition total = new Nutrition();
        groceryQuantity.forEach(
                (Grocery g, Double d) -> total.add(g.getNutrition())
        );
        return total;
    }
}
