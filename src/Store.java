import java.util.ArrayList;
import java.util.HashMap;

public class Store {

    private HashMap<Grocery, Double> groceryPrices;
    private ArrayList<Receipt> receipts;
    private String name;

    public void Store(String name){
        this.groceryPrices = new HashMap<Grocery, Double>();
        this.receipts = new ArrayList<Receipt>();
        this.name = name;
    }

    public double getPriceOf(Grocery grocery){
        Double price = groceryPrices.get(grocery);
        return price == null ? 0.0 : price.doubleValue();
    }

    public void addR(Receipt receipt){
        this.receipts.add(receipt);
    }
}
