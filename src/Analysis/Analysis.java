package Analysis;

import Database.*;
import Filters.FilterSet;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

public class Analysis {
    private ArrayList<Result> results;
    LocalDate firstDate, lastDate;

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

                    if(firstDate == null || firstDate.isAfter(r.getDate()))
                        firstDate = r.getDate();
                    if(lastDate == null || lastDate.isBefore(r.getDate()))
                        lastDate = r.getDate();
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
    public long getNumberOfDays() {
        if(this.firstDate == null || this.lastDate == null)
            return 1;
        return ChronoUnit.DAYS.between(this.firstDate, this.lastDate) + 1;
    }
    public double getAverageQuantityPerDay() {
        return this.getTotalQuantity() / this.getNumberOfDays();
    }
    public double getAverageServingsPerDay() {
        return this.getTotalServings() / this.getNumberOfDays();
    }
    public double getAveragePricePerDay() {
        return this.getTotalPrice() / this.getNumberOfDays();
    }
    public Nutrition getAverageNutritionPerDay() {
        double numberOfDays = getNumberOfDays();
        Nutrition totalNutrition = this.getTotalNutrition();

        return totalNutrition.multiply(1.0/numberOfDays);
    }
    public List<Store> getStoresMatching() {
        ArrayList<Store> stores = new ArrayList<>();

        for(Result r : this.results)
            if(!stores.contains(r.store))
                stores.add(r.store);

        return stores;
    }
    public List<Grocery> getGroceriesMatching() {
        ArrayList<Grocery> groceries = new ArrayList<>();

        for(Result r : this.results)
            if(!groceries.contains(r.grocery))
                groceries.add(r.grocery);

        return groceries;
    }
}