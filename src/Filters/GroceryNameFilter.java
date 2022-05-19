package Filters;

import Database.Grocery;

public class GroceryNameFilter extends Filter<Grocery> {

    private String name;

    public GroceryNameFilter(String name) {
        this.name = name;
    }
    public GroceryNameFilter(Filter<Grocery> child, String name) {
        super(child);
        this.name = name;
    }

    @Override
    public boolean accepts(Grocery grocery) {
        String g1 = grocery.getName();
        return (g1.equalsIgnoreCase(name)) && super.accepts(grocery);
    }
}
