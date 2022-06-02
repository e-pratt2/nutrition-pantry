package Database;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class StoreTest {

    @Test
    void StoreConstructorException(){
        assertThrows(IllegalArgumentException.class, ()->{
           Store s = new Store(null);
        });

        assertThrows(IllegalArgumentException.class, ()->{
            Store s = new Store("");
        });
    }

    @Test
    void getName() {
        Store s = new Store("Safeway");
        assertEquals("Safeway", s.getName());
    }

    @Test
    void getPriceOf() {

    }

    @Test
    void addReceipt() {
        LocalDate ld = LocalDate.parse("2012-12-12");
        Receipt r = new Receipt(ld);
        Grocery g = new Grocery("cabbage", new Nutrition(), 1.0);
        r.addGrocery(g,1);
        Store s = new Store("Safeway");
        s.addReceipt(r);
        assertArrayEquals(new Receipt[]{r}, s.getReceipts().toArray());

    }

    @Test
    void getReceipts() {
        LocalDate ld = LocalDate.parse("2012-12-12");
        Receipt r = new Receipt(ld);
        Grocery g = new Grocery("cabbage", new Nutrition(), 1.0);
        r.addGrocery(g,1);
        Store s = new Store("Safeway");
        s.addReceipt(r);
        assertArrayEquals(new Receipt[]{r}, s.getReceipts().toArray());

    }
}