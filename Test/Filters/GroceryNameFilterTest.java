package Filters;

import Database.Grocery;
import Database.Nutrition;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GroceryNameFilterTest {

    @Test
    void constructorException(){
        assertThrows(IllegalArgumentException.class, ()->{
           GroceryNameFilter g = new GroceryNameFilter(null);
        });

        assertThrows(IllegalArgumentException.class, ()->{
            GroceryNameFilter g = new GroceryNameFilter("");
        });

        Filter f = new Filter<>();

        assertThrows(IllegalArgumentException.class, ()->{
            GroceryNameFilter g = new GroceryNameFilter(f, null);
        });

        assertThrows(IllegalArgumentException.class, ()->{
            GroceryNameFilter g = new GroceryNameFilter(f, "");
        });

    }

    @Test
    void accepts() {
        GroceryNameFilter g = new GroceryNameFilter("cabbage");
        Nutrition n = new Nutrition();
        Grocery gg = new Grocery("cabbage", n);

        assertThrows(IllegalArgumentException.class, ()->{
            g.accepts(null);
        });

        assertTrue(g.accepts(gg));

        Grocery bad = new Grocery("potato", n);
        assertFalse(g.accepts(bad));
    }

    @Test
    void testToString() {

    }
}