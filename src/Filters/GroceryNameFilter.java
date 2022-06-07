package Filters;

import Database.Grocery;

import java.util.Locale;
import java.util.Objects;

/**
 * Filter that only passes groceries with the specified name (case-insensitive)
 */
public class GroceryNameFilter extends Filter<Grocery> {

    private final String name;

    /**
     * Construct the filter to accept groceries with the specified name.
     * @param name the case-insensitive name to check for.
     * @throws IllegalArgumentException on null or empty name.
     */

    //Creates a specific Grocery Name filter
    public GroceryNameFilter(String name) {
        if(name == null || name.isEmpty())
            throw new IllegalArgumentException("bad param in GroceryNameFilter");

        this.name = name;
    }

    /**
     * Decorate another filter with a grocery name filter.
     * @param child the filter to decorate. Will only pass if this filter passes and the child filter(s) pass.
     * @param name the case-insensitive name to check for.
     * @throws IllegalArgumentException on null or empty name.
     */

    //Creates a specific Grocery Name filter and allows another filter to wrap around
    public GroceryNameFilter(Filter<Grocery> child, String name) {
        super(child);
        if( name == null || name.isEmpty())
            throw new IllegalArgumentException("bad param in GroceryNameFilter");

        this.name = name;
    }

    /**
     * Test the given grocery for a name match. Also tests this grocery against the child filter, if present.
     * @param grocery The grocery to test against.
     * @return true if this filter and all it's children accept the grocery, false otherwise.
     * @throws IllegalArgumentException if grocery is null
     */

    //checks if the grocery passed into the filter is accepted
    @Override
    public boolean accepts(Grocery grocery) {
        if(grocery == null)
            throw new IllegalArgumentException("bad param accepts grocery filter");
        String g1 = grocery.getName();
        return g1.toLowerCase(Locale.ROOT)
                .contains(name.toLowerCase(Locale.ROOT)) && super.accepts(grocery);
    }

    @Override
    public String toString() {
        return "Grocery Name{" + name +"} -> " + super.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof GroceryNameFilter))
            return false;

        GroceryNameFilter other = (GroceryNameFilter) obj;

        return other.name.equals(this.name) && Objects.equals(other.child, this.child);
    }
}
