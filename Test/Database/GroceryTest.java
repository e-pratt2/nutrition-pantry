package Database;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class GroceryTest {

    @Test
    void GroceryConstructorException(){
        assertThrows(IllegalArgumentException.class, ()->{
            Nutrition n = new Nutrition(2.0,2.0,2.0,2.0,2.0,2.0);
            Grocery g = new Grocery(null, n);
        });

        assertThrows(IllegalArgumentException.class, ()->{
            Nutrition n = new Nutrition(2.0,2.0,2.0,2.0,2.0,2.0);
            Grocery g = new Grocery("", n);
        });

        assertThrows(IllegalArgumentException.class, ()->{
            Nutrition n = null;
            Grocery g = new Grocery("cabbage", n);
        });
    }

    @Test
    void getNutrition() {
        Nutrition n = new Nutrition();
        Grocery g = new Grocery("cabbage", n);
        assertEquals(new Nutrition(), g.getNutrition());
    }

    @Test
    void getName() {
        Nutrition n = new Nutrition();
        Grocery g = new Grocery("cabbage", n);
        assertEquals("cabbage", g.getName());
    }

    @Test
    void testToString() {
        Nutrition n = new Nutrition();
        Grocery g = new Grocery("cabbage", n);
        assertEquals("Grocery{cabbage, " + n.toString() + "}", g.toString());
    }

    @Test
    void testEquals(){
        Nutrition n = new Nutrition();
        Grocery g = new Grocery("cabbage", n);
        Grocery g1 = new Grocery("cabbage", n);
        Grocery g2 = new Grocery("carrot", n);
        assertTrue(g.equals(g1));
        assertFalse(g.equals(g2));
    }
}