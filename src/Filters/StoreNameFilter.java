package Filters;

import Database.Store;

public class StoreNameFilter extends Filter<Store> {
    private String name;

    public StoreNameFilter(String name) {

        if(name == null || name.equalsIgnoreCase(""))
            throw new IllegalArgumentException("bad param in StoreNameFilter");

        this.name = name;
    }
    public StoreNameFilter(Filter<Store> child, String name) {
        super(child);

        if(name == null || name.equalsIgnoreCase(""))
            throw new IllegalArgumentException("bad param in StoreNameFilter");
        this.name = name;
    }

    @Override
    public boolean accepts(Store store) {
        if(store == null)
            throw new IllegalArgumentException("bad param StoreNameFilter");

        return store.getName().equalsIgnoreCase(name) && super.accepts(store);
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof StoreNameFilter))
            return false;

        StoreNameFilter other = (StoreNameFilter) obj;

        return other.name.equals(this.name) && this.child.equals(other.child);
    }

    @Override
    public String toString() {
        return "Store Name{" + name +"} -> " + super.toString();
    }
}
