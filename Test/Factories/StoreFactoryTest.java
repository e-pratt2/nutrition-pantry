package Factories;

import Database.Store;
import UI.UIHelpers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class StoreFactoryTest {
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
    void createStore() {
        Store s = new Store("Safeway");
        setInput("Safeway");
        StoreFactory storeFactory = new StoreFactory();
        assertEquals(s,storeFactory.createStore());
    }
}