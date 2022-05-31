package Filters;

import java.util.Objects;

public class Filter <E>{
    protected Filter<E> child;
    public static final Filter AlwaysPass = new Filter<>();
    public Filter(Filter<E> child) {
        this.child = child;
    }
    public Filter() {
        this.child = null;
    }

    public boolean accepts(E e) {
        return child == null ? true : child.accepts(e);
    };

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
