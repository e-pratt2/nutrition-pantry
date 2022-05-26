package Filters;

import Database.Grocery;
import Database.Receipt;
import Database.Store;

public class FilterSet {
    private Filter<Store> storeFilter;
    private Filter<Grocery> groceryFilter;
    private Filter<Receipt> receiptFilter;

    public static final FilterSet AlwaysPass = new FilterSet();

    public FilterSet() {
        storeFilter = Filter.AlwaysPass;
        groceryFilter = Filter.AlwaysPass;
        receiptFilter = Filter.AlwaysPass;
    }
    public FilterSet(Filter<Store> storeFilter, Filter<Grocery> groceryFilter, Filter<Receipt> receiptFilter) {
        if(storeFilter == null)
            this.storeFilter = Filter.AlwaysPass;
        if(groceryFilter == null)
            this.groceryFilter = Filter.AlwaysPass;
        if(receiptFilter == null)
            this.receiptFilter = Filter.AlwaysPass;
        this.storeFilter = storeFilter;
        this.groceryFilter = groceryFilter;
        this.receiptFilter = receiptFilter;
    }


    public Filter<Store> getStore() {
        return storeFilter;
    }

    public Filter<Grocery> getGrocery() {
        return groceryFilter;
    }

    public Filter<Receipt> getReceipt() {
        return receiptFilter;
    }

    @Override
    public String toString() {
        return "FilterSet{"
                + "\n\tStore: " + storeFilter.toString()
                + "\n\tGrocery: " + groceryFilter.toString()
                + "\n\tReceipt: " + receiptFilter.toString()
                + "\n}";
    }
}
