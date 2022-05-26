package Filters;

import Database.Grocery;
import Database.Receipt;
import Database.Store;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FilterSetTest {

    @Test
    void getStore() {
        Filter<Store> s = new Filter<Store>();
        FilterSet filterSet = new FilterSet(s,Filter.AlwaysPass,Filter.AlwaysPass);
        assertEquals(s, filterSet.getStore());
    }

    @Test
    void getGrocery() {
        Filter<Grocery> g = new Filter<Grocery>();
        FilterSet filterSet = new FilterSet(Filter.AlwaysPass,g ,Filter.AlwaysPass);
        assertEquals(g, filterSet.getGrocery());
    }

    @Test
    void getReceipt() {
        Filter<Receipt> r = new Filter<Receipt>();
        FilterSet filterSet = new FilterSet(Filter.AlwaysPass,Filter.AlwaysPass ,r);
        assertEquals(r, filterSet.getReceipt());
    }

    @Test
    void testToString() {
        Filter<Store> s = new Filter<Store>();
        Filter<Grocery> g = new Filter<Grocery>();
        Filter<Receipt> r = new Filter<Receipt>();
        FilterSet sf = new FilterSet();
        assertEquals("FilterSet{\n\tStore: "
                + s.toString() +"\n\tGrocery: "
                + g.toString() +"\n\tReceipt: "
                + r.toString() +"\n}",sf.toString());

    }
}