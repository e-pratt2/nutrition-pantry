package Filters;

import Database.Grocery;

import java.util.Objects;

public class GroceryNameFilter extends Filter<Grocery> {

    private String name;

    public GroceryNameFilter(String name) {
        if(name == null || name.equalsIgnoreCase(""))
            throw new IllegalArgumentException("bad param in GroceryNameFilter");

        this.name = name;
    }
    public GroceryNameFilter(Filter<Grocery> child, String name) {

        super(child);
        if( name == null || name.equalsIgnoreCase(""))
            throw new IllegalArgumentException("bad param in GroceryNameFilter");

        this.name = name;
    }

    @Override
    public boolean accepts(Grocery grocery) {
        if(grocery == null)
            throw new IllegalArgumentException("bad param accepts grocery filter");
        String g1 = grocery.getName();
        return (g1.equalsIgnoreCase(name)) && super.accepts(grocery);
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
