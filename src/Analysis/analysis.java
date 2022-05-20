package Analysis;

import Database.SerializableDatabase;
import Database.Grocery;
import Database.Nutrition;
import Database.Receipt;
import Database.Store;
import Filters.Filter;
import Filters.FilterSet;

public class analysis {

    public double avgPrice(FilterSet filterSet){
        return getAveragePrice(filterSet.getStore(), filterSet.getReceipt(), filterSet.getGrocery());
    }

    public Nutrition AvgNutrition(FilterSet filterSet){
        return getAverageNutrition(filterSet.getStore(), filterSet.getReceipt(), filterSet.getGrocery());
    }

    public double totalPrice(FilterSet filterSet){
        return getTotalPrice(filterSet.getStore() , filterSet.getReceipt(), filterSet.getGrocery());
    }

    public Nutrition totalNutrition(FilterSet filterSet){
        return getTotalNutrition(filterSet.getStore(), filterSet.getReceipt(), filterSet.getGrocery());
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

                for(Grocery g : r.getGroceries()) {
                    if(!groceryFilter.accepts(g))
                        continue;

                    Nutrition subTotal = g.getNutrition().multiply(r.getQuantityOf(g));

                    total.add(subTotal);
                }
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

                for(Grocery g : r.getGroceries()) {
                    if(!groceryFilter.accepts(g))
                        continue;

                    Nutrition subTotal = g.getNutrition().multiply(r.getQuantityOf(g));

                    total.add(subTotal);
                    foundInstances++;
                }
            }
        }
        return total.multiply(1.0/foundInstances);
    }

    public double getQuantityOf(Filter<Store> storeFilter, Filter<Receipt> receiptFilter, Filter<Grocery> groceryFilter) {
        double total = 0.0;

        for(Store s : SerializableDatabase.getInstance().getStores()){
            if(!storeFilter.accepts(s))
                continue;

            for(Receipt r : s.getReceipts()){
                if(!receiptFilter.accepts(r))
                    continue;

                for(Grocery g : r.getGroceries()) {
                    if(!groceryFilter.accepts(g))
                        continue;

                    total += r.getQuantityOf(g);
                }
            }
        }

        return total;
    }
}
