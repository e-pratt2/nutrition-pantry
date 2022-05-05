package Database;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Store implements Serializable {
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

    public void addReceipt(Receipt receipt){
        this.receipts.add(receipt);
    }

    public double getTotalPrice() {
        double total = 0.0;
        for(Receipt r : this.receipts) {
            for(Grocery g : r.getGroceries()) {
                total += this.getPriceOf(g) * r.getQuantityOf(g);
            }
        }
        return total;
    }
    public double getAveragePrice() {
        return getTotalPrice()/receipts.size();
    }

    public Nutrition getTotalNutrition() {
        Nutrition total = new Nutrition();
        receipts.forEach(
                (Receipt r) -> total.add(r.getTotalNutrition())
        );
        return total;
    }
}
