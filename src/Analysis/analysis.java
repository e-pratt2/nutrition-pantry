package Analysis;

import Database.SerializableDatabase;
import Database.Grocery;
import Database.Nutrition;
import Database.Receipt;
import Database.Store;
import Filters.Filter;

public class analysis {

    public double avgPrice(Filter<Store> storeFilter, Filter<Receipt> receiptFilter, Filter<Grocery> groceryFilter){
        return getAveragePrice(storeFilter, receiptFilter, groceryFilter);
    }

    public Nutrition AvgNutrition(Filter<Store> filter, Filter<Receipt> filter1, Filter<Grocery> filter2){
        return null;
    }

    public double totalPrice(Filter<Store> storeFilter, Filter<Receipt> receiptFilter, Filter<Grocery> groceryFilter){
        return getTotalPrice(storeFilter ,receiptFilter, groceryFilter);
    }

    public Nutrition totalNutrition(Filter<Store> storeFilter, Filter<Receipt> receiptFilter, Filter<Grocery> groceryFilter){
        return getTotalNutrition(storeFilter, receiptFilter, groceryFilter);
    }



    private double getTotalPrice(Filter<Store> storeFilter, Filter<Receipt> receiptFilter, Filter<Grocery> groceryFilter) {
        double total = 0.0;
        for(Store s : SerializableDatabase.getInstance().getStores()) {
            if(!storeFilter.accepts(s))
                continue;

            for (Receipt r : s.getReceipts()) {
                if(!receiptFilter.accepts(r))
                    continue;

                for (Grocery g : r.getGroceries()) {
                    if (!groceryFilter.accepts(g))
                        continue;

                    total += s.getPriceOf(g) * r.getQuantityOf(g);
                }
            }
        }
        return total;
    }
    private double getAveragePrice(Filter<Store> storeFilter, Filter<Receipt> receiptFilter, Filter<Grocery> groceryFilter) {
        double total = 0.0;
        int foundInstances = 0;
        for(Store s : SerializableDatabase.getInstance().getStores()) {
            if(!storeFilter.accepts(s))
                continue;

            for (Receipt r : s.getReceipts()) {
                if(!receiptFilter.accepts(r))
                    continue;

                for (Grocery g : r.getGroceries()) {
                    if (!groceryFilter.accepts(g))
                        continue;

                    total += s.getPriceOf(g) * r.getQuantityOf(g);
                    foundInstances++;
                }
            }
        }
        return total/foundInstances;
    }

    private Nutrition getTotalNutrition(Filter<Store> storeFilter, Filter<Receipt> receiptFilter, Filter<Grocery> groceryFilter) {
        Nutrition total = new Nutrition();
        for(Store s : SerializableDatabase.getInstance().getStores()) {
            if(!storeFilter.accepts(s))
                continue;

            for(Receipt r : s.getReceipts()){
                if (!receiptFilter.accepts(r))
                    continue;

                total.add(r.getTotalNutrition(groceryFilter));
            }
        }
        return total;
    }

    private Nutrition getAverageNutrition(Filter<Store> storeFilter, Filter<Receipt> receiptFilter, Filter<Grocery> groceryFilter) {
        Nutrition total = new Nutrition();
        int foundInstances = 0;
        for(Store s : SerializableDatabase.getInstance().getStores()) {
            if(!storeFilter.accepts(s))
                continue;

            for(Receipt r : s.getReceipts()){
                if (!receiptFilter.accepts(r))
                    continue;

                total.add(r.getTotalNutrition(groceryFilter));
                foundInstances++;
            }
        }
        return total.multiply(1.0/foundInstances);
    }
}
