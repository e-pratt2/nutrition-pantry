package Filters;

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
}
