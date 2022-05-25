package Database;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ReceiptTest {

    @Test
    void ReceiptException(){
        assertThrows(IllegalArgumentException.class, ()->{
           new Receipt(null);
        });
    }

    @Test
    void getQuantityOf() {
        LocalDate ld = LocalDate.parse("2012-12-12");
        Receipt r = new Receipt(ld);
        Grocery g = new Grocery("cabbage", new Nutrition());
        assertEquals(0, r.getQuantityOf(g));

        r.addGrocery(g, 2);
        assertEquals(2, r.getQuantityOf(g));
    }

    @Test
    void getDate() {
        LocalDate ld = LocalDate.parse("2012-12-12");
        Receipt r = new Receipt(ld);
        assertEquals(ld, r.getDate());
    }

    @Test
    void addGrocery() {
        LocalDate ld = LocalDate.parse("2012-12-12");
        Receipt r = new Receipt(ld);
        Grocery g = new Grocery("cabbage", new Nutrition());

        r.addGrocery(g, 2);
        assertEquals(2, r.getQuantityOf(g));
    }

    @Test
    void getGroceries() {
        LocalDate ld = LocalDate.parse("2012-12-12");
        Receipt r = new Receipt(ld);
        Grocery g = new Grocery("cabbage", new Nutrition());
        r.addGrocery(g, 2);
        Grocery[] gg = {g};
        assertArrayEquals(gg, r.getGroceries().toArray());
        //TODO:finish

    }
}