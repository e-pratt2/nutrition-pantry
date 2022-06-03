package Analysis;

import Database.*;
import Filters.FilterSet;

import java.util.ArrayList;

public class Analysis {
    private ArrayList<Result> results;

    private class Result {
        private final Store store;
        private final Receipt receipt;
        private final Grocery grocery;

        public Result(Store s, Receipt r, Grocery g) {
            this.store = s;
            this.receipt = r;
            this.grocery = g;
        }

        double getTotalPrice() {
            return this.receipt.getQuantityOf(this.grocery)
                    * this.store.getPriceOf(this.grocery);
        }
        double getServings() {
            return this.grocery.getServingsPerContainer()
                    * this.receipt.getQuantityOf(this.grocery);
        }
        double getQuantity() {
            return this.receipt.getQuantityOf(grocery);
        }
        Nutrition getTotalNutrition() {
            return this.grocery.getTotalNutrition()
                    .multiply(this.receipt.getQuantityOf(this.grocery));
        }
    }

    public Analysis(FilterSet fs) {
        this.results = new ArrayList<>();

        for(Store s : SerializableDatabase.getInstance().getStores()) {
            if(!fs.getStore().accepts(s))
                continue;

            for(Receipt r : s.getReceipts()) {
                if(!fs.getReceipt().accepts(r))
                    continue;

                for(Grocery g : r.getGroceries()) {
                    if(!fs.getGrocery().accepts(g))
                        continue;

                    this.results.add(new Result(s, r, g));
                }
            }
        }
    }

    public double getTotalPrice() {
        double total = 0.0;

        for(Result r : this.results)
            total += r.getTotalPrice();

        return total;
    }
    public Nutrition getTotalNutrition() {
        Nutrition n = new Nutrition();

        for(Result r : this.results)
            n.add(r.getTotalNutrition());

        return n;
    }
    public double getTotalServings() {
        double total = 0.0;

        for(Result r : this.results)
            total += r.getServings();

        return total;
    }
    public double getTotalQuantity() {
        double total = 0.0;

        for(Result r : this.results)
            total += r.getQuantity();

        return total;
    }
    public Nutrition getAverageNutritionPerPrice() {
        double totalPrice = this.getTotalPrice();
        Nutrition totalNutrition = this.getTotalNutrition();

        return totalNutrition.multiply(1.0/totalPrice);
    }
    public double getAverageServingsPerPrice() {
        double servings = this.getTotalServings();
        double totalPrice = this.getTotalPrice();

        return servings / totalPrice;
    }
    public double getAverageQuantityPerPrice() {
        double quantity = this.getTotalQuantity();
        double totalPrice = this.getTotalPrice();

        return quantity / totalPrice;
    }
}
