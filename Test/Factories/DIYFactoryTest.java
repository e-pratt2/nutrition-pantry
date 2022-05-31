package Factories;

import Database.Grocery;
import Database.Nutrition;
import UI.UIHelpers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class DIYFactoryTest {

    @BeforeEach
    void resetScanner() throws NoSuchFieldException, IllegalAccessException {
        Field kb = UIHelpers.class.getDeclaredField("kb");
        kb.setAccessible(true);
        kb.set(null, null);
    }

    //Create a way to emulate user input
    void setInput(String s) {
        try {
            InputStream testInput = new ByteArrayInputStream(s.getBytes(StandardCharsets.UTF_8));
            System.setIn(testInput);
            resetScanner();
        } catch (Exception e) {
            throw new RuntimeException("Couldn't set inputs for UIHelpersTest");
        }
    }

    @Test
    void createGrocery() {

        Grocery g = new Grocery("cabbage", new Nutrition());
        DIYFactory d = new DIYFactory();
        setInput("cabbage\n0\n0\n0\n0\n0\n0\n");
        assertEquals(g, d.createGrocery());
    }
}