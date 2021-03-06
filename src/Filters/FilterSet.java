package Filters;

import Database.Grocery;
import Database.Receipt;
import Database.Store;

import java.util.Objects;

/**
 * Represents a set of filters capable of filtering all three kinds of database objects.
 */
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

    //Stores all of the used filters
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
    public boolean equals(Object obj) {
        if(!(obj instanceof FilterSet))
            return false;

        FilterSet other = (FilterSet)obj;

        return Objects.equals(other.groceryFilter,this.groceryFilter) &&
                Objects.equals(other.receiptFilter,this.receiptFilter) &&
                Objects.equals(other.storeFilter,this.storeFilter);
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
