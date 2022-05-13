package Filters;

import Database.Grocery;

public class GroceryNameFilter implements Filter<Grocery> {

    private String g;

    public GroceryNameFilter(String g) {
        this.g = g;
    }

    @Override
    public boolean accepts(Grocery grocery) {
        String g1 = grocery.getName();
        return (g1.equalsIgnoreCase(g));
    }
}
