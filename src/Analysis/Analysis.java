package Analysis;

import Database.SerializableDatabase;
import Database.Grocery;
import Database.Nutrition;
import Database.Receipt;
import Database.Store;
import Filters.Filter;
import Filters.FilterSet;

import java.util.ArrayList;
import java.util.List;

public class Analysis {

    public static Nutrition averageNutritionPerPrice(FilterSet fs) {
        List<Store> stores = findMatchingStores(fs);
        List<Receipt> receipts = findMatchingReceipts(stores, fs);
        List<Grocery> groceries = findMatchingGroceries(receipts, fs);

        double totalPrice = 0.0;
        Nutrition totalNutrition = new Nutrition();

        for(Grocery g : groceries) {

        }
    }

    public static void avgPrice(FilterSet filterSet){
        System.out.println("Average Price:");
        System.out.println(getAveragePrice(filterSet.getStore(), filterSet.getReceipt(), filterSet.getGrocery()));
    }

    public static void AvgNutrition(FilterSet filterSet){
        System.out.println("Average Nutrition:");
        System.out.println(getAverageNutrition(filterSet.getStore(), filterSet.getReceipt(), filterSet.getGrocery()));
    }

    public static void totalPrice(FilterSet filterSet){
        System.out.println("Total Price:");
        System.out.println(getTotalPrice(filterSet.getStore() , filterSet.getReceipt(), filterSet.getGrocery()));
    }

    public static void totalNutrition(FilterSet filterSet){
        System.out.println("Total Nutrition:");
        System.out.println(getTotalNutrition(filterSet.getStore(), filterSet.getReceipt(), filterSet.getGrocery()));
    }

    private static List<Store> findMatchingStores(FilterSet fs) {
        ArrayList<Store> stores = new ArrayList<>();

        for(Store s : SerializableDatabase.getInstance().getStores())
            if(fs.getStore().accepts(s))
                stores.add(s);

        return stores;
    }
    private static List<Receipt> findMatchingReceipts(List<Store> stores, FilterSet fs) {
        ArrayList<Receipt> receipts = new ArrayList<>();

        for(Store s : stores)
            for(Receipt r : s.getReceipts())
                if(fs.getReceipt().accepts(r))
                    receipts.add(r);

        return receipts;
    }
    private static List<Grocery> findMatchingGroceries(List<Receipt> receipts, FilterSet fs) {
        ArrayList<Grocery> groceries = new ArrayList<>();

        for(Receipt r : receipts)
            for(Grocery g : r.getGroceries())
                if(fs.getGrocery().accepts(g))
                    groceries.add(g);

        return groceries;
    }

    private static double getTotalPrice(Filter<Store> storeFilter, Filter<Receipt> receiptFilter, Filter<Grocery> groceryFilter) {
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

    private static double getAveragePrice(Filter<Store> storeFilter, Filter<Receipt> receiptFilter, Filter<Grocery> groceryFilter) {
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

    private static Nutrition getTotalNutrition(Filter<Store> storeFilter, Filter<Receipt> receiptFilter, Filter<Grocery> groceryFilter) {
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

                    Nutrition subTotal = g.getNutritionPerServing().multiply(r.getQuantityOf(g));

                    total.add(subTotal);
                }
            }
        }
        return total;
    }

    private static Nutrition getAverageNutrition(Filter<Store> storeFilter, Filter<Receipt> receiptFilter, Filter<Grocery> groceryFilter) {
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

                    Nutrition subTotal = g.getNutritionPerServing().multiply(r.getQuantityOf(g));

                    total.add(subTotal);
                    foundInstances++;
                }
            }
        }
        return total.multiply(1.0/foundInstances);
    }

    public static double getQuantityOf(Filter<Store> storeFilter, Filter<Receipt> receiptFilter, Filter<Grocery> groceryFilter) {
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
