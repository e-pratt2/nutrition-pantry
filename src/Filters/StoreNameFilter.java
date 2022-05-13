package Filters;

import Database.Store;

public class StoreNameFilter implements Filter<Store> {
    private String name;

    public StoreNameFilter(String name) {
        this.name = name;
    }

    @Override
    public boolean accepts(Store store) {
        return store.getName().equalsIgnoreCase(name);
    }
}
