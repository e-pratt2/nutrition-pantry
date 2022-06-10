package Database;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NutritionTest {

    @Test
    void NutritionException(){
        assertThrows(IllegalArgumentException.class, ()->{
           new Nutrition(-1.0,0.0,0.0,0.0,0.0,0.0);
        });

        assertThrows(IllegalArgumentException.class, ()->{
            new Nutrition(0.0,-1.0,0.0,0.0,0.0,0.0);
        });

        assertThrows(IllegalArgumentException.class, ()->{
            new Nutrition(0.0,0.0,-1.0,0.0,0.0,0.0);
        });

        assertThrows(IllegalArgumentException.class, ()->{
            new Nutrition(0.0,0.0,0.0,-1.0,0.0,0.0);
        });

        assertThrows(IllegalArgumentException.class, ()->{
            new Nutrition(0.0,0.0,0.0,0.0,-1.0,0.0);
        });

        assertThrows(IllegalArgumentException.class, ()->{
            new Nutrition(0.0,0.0,0.0,0.0,0.0,-1.0);
        });
    }

    @Test
    void getCalories() {
        Nutrition n = new Nutrition();
        assertEquals(0, n.getCalories());
    }

    @Test
    void getFat() {
        Nutrition n = new Nutrition();
        assertEquals(0, n.getFat());
    }

    @Test
    void getFiber() {
        Nutrition n = new Nutrition();
        assertEquals(0, n.getFiber());
    }

    @Test
    void getProtein() {
        Nutrition n = new Nutrition();
        assertEquals(0, n.getProtein());
    }

    @Test
    void getSodium() {
        Nutrition n = new Nutrition();
        assertEquals(0, n.getSodium());
    }

    @Test
    void getSugar() {
        Nutrition n = new Nutrition();
        assertEquals(0, n.getSugar());
    }

    @Test
    void add() {
        Nutrition n = new Nutrition(1.0,1.0,1.0,1.0,1.0,1.0);
        Nutrition n1 = new Nutrition(1.0,1.0,1.0,1.0,1.0,1.0);
        n.add(n1);

        assertEquals(2.0, n.getCalories());
        assertEquals(2.0, n.getFat());
        assertEquals(2.0, n.getSugar());
        assertEquals(2.0, n.getFiber());
        assertEquals(2.0, n.getProtein());
        assertEquals(2.0, n.getSodium());
    }

    @Test
    void multiply() {
        Nutrition n = new Nutrition(1.0,1.0,1.0,1.0,1.0,1.0);
        Nutrition n1 =  n.multiply(2);

        assertEquals(2.0, n1.getCalories());
        assertEquals(2.0, n1.getFat());
        assertEquals(2.0, n1.getSugar());
        assertEquals(2.0, n1.getFiber());
        assertEquals(2.0, n1.getProtein());
        assertEquals(2.0, n1.getSodium());
    }

    @Test
    void testToString() {
        Nutrition n = new Nutrition();
        assertEquals("Nutrition{Calories: 0.0, Fat: 0.0, Sugar: 0.0, Fiber: 0.0, Protein: 0.0, Sodium: 0.0}", n.toString());
    }

    @Test
    void testEquals() {
        Nutrition n = new Nutrition();
        Nutrition n1 = new Nutrition();
        Nutrition n2 = new Nutrition(2.0,2.0,2.0,2.0,2.0,2.0);
        assertTrue(n.equals(n1));
        assertFalse(n.equals(n2));
    }
}