package Filters;

import java.util.Objects;

/**
 * A class representing the decorator interface for a generic filter chain.
 * @param <E> The type of objects examined by this filter
 */

//Combines all of the used filters into one nested filter
public class Filter <E>{
    protected Filter<E> child;

    /**
     * A static filter that always passes objects of any type - a "null" filter. Useful for decoration.
     */
    public static final Filter AlwaysPass = new Filter<>();

    /**
     * Decorate this filter around a child filter.
     * @param child the filter to wrap around. Calls child filter after this filter passes, effectively and-ing their results.
     */
    public Filter(Filter<E> child) {
        this.child = child;
    }


    /**
     * Construct a filter with no child. Will always pass objects.
     */
    public Filter() {
        this.child = null;
    }

    /**
     * Check whether the entire filter chain accepts the object
     * @param e the object to test
     * @return whether the filter chain (this filter and all children filters) accepts this object.
     */
    public boolean accepts(E e) {
        return child == null || child.accepts(e);
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Filter))
            return false;

        Filter other = (Filter)obj;

        return Objects.equals(this.child, other.child);
    }

    @Override
    public String toString() {
        return child == null ? "Pass" : child.toString();
    }
}
