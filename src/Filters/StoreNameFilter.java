package Filters;

import Database.Store;

public class StoreNameFilter extends Filter<Store> {
    private String name;

    public StoreNameFilter(String name) {
        this.name = name;
    }
    public StoreNameFilter(Filter<Store> child, String name) {
        super(child);
        this.name = name;
    }

    @Override
    public boolean accepts(Store store) {
        return store.getName().equalsIgnoreCase(name) && super.accepts(store);
    }


    @Override
    public String toString() {
        return "Store Name{" + name +"}" + super.toString();
    }
}
