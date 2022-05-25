package Factories;

import Database.Grocery;
import Database.Nutrition;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class UPCGroceryFactoryTest {

    //Set System.in to predefined input
    void setInput(String s) {
        InputStream testInput = new ByteArrayInputStream(s.getBytes(StandardCharsets.UTF_8));
        System.setIn(testInput);
    }

    @Test
    void createGrocery() {
        InputStream is = System.in;


        setInput("Nature Valley Dark Cherry\n\n016000508422\n\n");

        Grocery g = new UPCGroceryFactory().createGrocery();

        Grocery expected = new Grocery("Nature Valley Dark Cherry", new Nutrition(
                150.0, 4.0, 8.0, 2.0, 2.0, 0.070
        ));


        assertEquals(expected, g);

        System.setIn(is);
    }

    @Test
    void validateCode() {
        //Good upc - nature valley granola bar
        assertTrue(UPCGroceryFactory.validateCode("016000508422"));

        //Bad upc - corrupted digit
        assertFalse(UPCGroceryFactory.validateCode("016010508422"));

        //Bad upc - too long
        assertFalse(UPCGroceryFactory.validateCode("0160105546508422"));

        //Bad upc - bad chars
        assertFalse(UPCGroceryFactory.validateCode("01601050f8422"));
    }

    @Test
    void parseJSON() {
        String testJson = "{" +
                "\"status\": 1," +
                "\"product\":{\"nutriments\":{" +
                "\"energy-kcal_serving\": 10.0," +
                "\"fat_serving\": 5.0," +
                "\"proteins_serving\":15.5," +
                "}}}";

        Nutrition n = new Nutrition(10.0, 5.0, 0.0, 0.0, 15.5, 0.0);

        assertEquals(n, new UPCGroceryFactory().parseJSON(testJson));
    }
}