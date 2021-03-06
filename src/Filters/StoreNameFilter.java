package Filters;

import Database.Store;

import java.util.Locale;
import java.util.Objects;

/**
 * A filter that matches for stores of a given name, case-insensitive.
 */
public class StoreNameFilter extends Filter<Store> {
    private final String name;


    /**
     * Create a store name filter without decorating, matching the given name.
     * @param name the name to check for, case-insensitive
     * @throws IllegalArgumentException if name is null or empty.
     */

    //Creates a specific Store Name filter
    public StoreNameFilter(String name) {
        if(name == null || name.isEmpty())
            throw new IllegalArgumentException("bad param in StoreNameFilter");

        this.name = name;
    }


    /**
     * Decorate another filter with a new store name filter. Passes only if this filter and the child filter passes.
     * @param child the filter to decorate. May be null.
     * @param name the name to check for, case-insensitive.
     * @throws IllegalArgumentException if name is null or empty
     */

    //Creates a specific Grocery Name filter and allows another filter to wrap around this filter
    public StoreNameFilter(Filter<Store> child, String name) {
        super(child);

        if(name == null || name.isEmpty())
            throw new IllegalArgumentException("bad param in StoreNameFilter");
        this.name = name;
    }

    /**
     * Test the given store with this filter.
     * @param store the store to check against.
     * @return true if the store name matches, and the child filters also accept it.
     * @throws IllegalArgumentException if store is null
     */

    //checks if the store object is accepted by the filter
    @Override
    public boolean accepts(Store store) {
        if(store == null)
            throw new IllegalArgumentException("bad param StoreNameFilter");

        return store.getName().toLowerCase(Locale.ROOT)
                .contains(name.toLowerCase(Locale.ROOT)) && super.accepts(store);
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof StoreNameFilter))
            return false;

        StoreNameFilter other = (StoreNameFilter) obj;

        return other.name.equals(this.name) && Objects.equals(other.child, this.child);
    }

    @Override
    public String toString() {
        return "Store Name{" + name +"} -> " + super.toString();
    }
}
