package Database;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class GroceryTest {

    @Test
    void GroceryConstructorException(){
        assertThrows(IllegalArgumentException.class, ()->{
            Nutrition n = new Nutrition(2.0,2.0,2.0,2.0,2.0,2.0);
            Grocery g = new Grocery(null, n, 1.0);
        });

        assertThrows(IllegalArgumentException.class, ()->{
            Nutrition n = new Nutrition(2.0,2.0,2.0,2.0,2.0,2.0);
            Grocery g = new Grocery("", n, 1.0);
        });

        assertThrows(IllegalArgumentException.class, ()->{
            Nutrition n = null;
            Grocery g = new Grocery("cabbage", n, 1.0);
        });

        assertThrows(IllegalArgumentException.class, ()->{
            Nutrition n = new Nutrition();
            Grocery g = new Grocery("cabbage", n, -251.0);
        });
    }

    @Test
    void getNutrition() {
        Nutrition n = new Nutrition();
        Grocery g = new Grocery("cabbage", n, 1.0);
        assertEquals(new Nutrition(), g.getNutritionPerServing());
    }

    @Test
    void getName() {
        Nutrition n = new Nutrition();
        Grocery g = new Grocery("cabbage", n, 1.0);
        assertEquals("cabbage", g.getName());
    }

    @Test
    void testToString() {
        Nutrition n = new Nutrition();
        Grocery g = new Grocery("cabbage", n, 1.0);
        assertEquals("Grocery{cabbage, " + n.toString() + ", 1.0 servings}", g.toString());
    }

    @Test
    void testEquals(){
        Nutrition n = new Nutrition();
        Grocery g = new Grocery("cabbage", n, 1.0);
        Grocery g1 = new Grocery("cabbage", n, 1.0);
        Grocery g2 = new Grocery("carrot", n, 1.0);
        assertTrue(g.equals(g1));
        assertFalse(g.equals(g2));
    }
}