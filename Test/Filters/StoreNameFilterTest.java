package Filters;

import Database.Store;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StoreNameFilterTest {

    @Test
    void constructorException(){
        assertThrows(IllegalArgumentException.class, ()->{
           StoreNameFilter s = new StoreNameFilter(null);
        });

        assertThrows(IllegalArgumentException.class, ()->{
            StoreNameFilter s = new StoreNameFilter("");
        });

        Filter f = new Filter();
        assertThrows(IllegalArgumentException.class, ()->{
            StoreNameFilter s = new StoreNameFilter(f,"");
        });

        assertThrows(IllegalArgumentException.class, ()->{
            StoreNameFilter s = new StoreNameFilter(f,null);
        });
    }

    @Test
    void accepts() {
        StoreNameFilter s = new StoreNameFilter("Safeway");
        assertThrows(IllegalArgumentException.class, ()->{
            s.accepts(null);
        });

        Store st = new Store("Safeway");
        assertTrue(s.accepts(st));

        st = new Store("Walmart");
        assertFalse(s.accepts(st));
    }

    @Test
    void testToString() {
        Store s = new Store("Safeway");
        StoreNameFilter st = new StoreNameFilter("Safeway");
        assertEquals("Store Name{Safeway} -> Pass", st.toString());
    }
}