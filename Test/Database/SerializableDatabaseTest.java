package Database;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class SerializableDatabaseTest {

    //@BeforeAll
    //void beforeAll(){
    //}

    @Test
    void getInstance() {
        SerializableDatabase sd = SerializableDatabase.getInstance();
        Grocery g = new Grocery("cabbage", new Nutrition());
        sd.addGrocery(g);

        assertArrayEquals( new Grocery[]{g}, sd.getGroceries().toArray());
    }

    @Test
    void hasInstance() {
        assertFalse(SerializableDatabase.hasInstance());

        SerializableDatabase sd = SerializableDatabase.getInstance();
        assertTrue(SerializableDatabase.hasInstance());
    }

    @Test
    void saveLoadInstance() throws IOException {
        SerializableDatabase sd = SerializableDatabase.getInstance();
        Grocery g = new Grocery("cabbage", new Nutrition());
        sd.addGrocery(g);
        SerializableDatabase.saveInstance("out.txt");

        SerializableDatabase.loadInstance("out.txt");

        assertArrayEquals( new Grocery[]{g}, sd.getGroceries().toArray());


    }

    @Test
    void getGroceries() {
        SerializableDatabase sd = SerializableDatabase.getInstance();
        Grocery g = new Grocery("cabbage", new Nutrition());
        sd.addGrocery(g);

        assertArrayEquals( new Grocery[]{g}, sd.getGroceries().toArray());
    }

    @Test
    void getStores() {
        SerializableDatabase sd = SerializableDatabase.getInstance();
        Store s = new Store("Safeway");
        sd.addStore(s);
        assertArrayEquals( new Store[]{s}, sd.getStores().toArray());
    }

    @Test
    void addGrocery() {
        SerializableDatabase sd = SerializableDatabase.getInstance();
        Grocery g = new Grocery("cabbage", new Nutrition());
        sd.addGrocery(g);
        assertArrayEquals( new Grocery[]{g}, sd.getGroceries().toArray());
    }

    @Test
    void addStore() {
        SerializableDatabase sd = SerializableDatabase.getInstance();
        Store s = new Store("Safeway");
        sd.addStore(s);
        assertArrayEquals( new Store[]{s}, sd.getStores().toArray());
    }

    @Test
    void addReceipt() {
        SerializableDatabase sd = SerializableDatabase.getInstance();
        Grocery g = new Grocery("cabbage", new Nutrition());
        Store s = new Store("Safeway");
        LocalDate ld = LocalDate.parse("2012-12-12");
        Receipt r = new Receipt(ld);
        r.addGrocery(g,1);
        sd.addGrocery(g);
        sd.addStore(s);
        sd.addReceipt(r,s);
        assertArrayEquals(new Receipt[]{r}, s.getReceipts().toArray());
    }
}