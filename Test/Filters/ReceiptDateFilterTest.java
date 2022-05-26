package Filters;

import Database.Receipt;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ReceiptDateFilterTest {

    @Test
    void ConstructorException(){
        LocalDate ld = LocalDate.parse("2012-12-12");
        assertThrows(IllegalArgumentException.class, ()->{
            ReceiptDateFilter r = new ReceiptDateFilter(null, ld);
        });

        assertThrows(IllegalArgumentException.class, ()->{
            ReceiptDateFilter r = new ReceiptDateFilter(ld, null);
        });

        Filter f = new Filter();

        assertThrows(IllegalArgumentException.class, ()->{
            ReceiptDateFilter r = new ReceiptDateFilter(f,null, ld);
        });

        assertThrows(IllegalArgumentException.class, ()->{
            ReceiptDateFilter r = new ReceiptDateFilter(f, ld, null);
        });
    }

    @Test
    void accepts() {
        LocalDate ld = LocalDate.parse("2012-12-12");
        LocalDate ld1 = LocalDate.parse("2020-12-12");
        LocalDate ld2 = LocalDate.parse("2010-12-12");
        LocalDate ld3 = LocalDate.parse("2013-12-12");

        ReceiptDateFilter rf = new ReceiptDateFilter(ld,ld1);

        assertThrows(IllegalArgumentException.class, ()->{
            rf.accepts(null);
        });

        Receipt r = new Receipt(ld3);
        assertTrue(rf.accepts(r));

        r = new Receipt(ld2);
        assertFalse(rf.accepts(r));
    }

    @Test
    void testToString() {
        LocalDate ld = LocalDate.parse("2012-12-12");
        LocalDate ld1 = LocalDate.parse("2020-12-12");
        String str = "Receipt Date{" + ld + " to " + ld1 +"} ->Pass";

        ReceiptDateFilter r = new ReceiptDateFilter(ld,ld1);

        assertEquals(str, r.toString());
    }
}