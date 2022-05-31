package UI;

import Filters.FilterSet;
import Filters.GroceryNameFilter;
import Filters.ReceiptDateFilter;
import Filters.StoreNameFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CommandLineTest {

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
    void fetchInput() throws NoSuchFieldException, IllegalAccessException {

        CommandLine obj = new CommandLine();
        Field lines = CommandLine.class.getDeclaredField("lines");
        lines.setAccessible(true);

        setInput("anna\n");

        obj.fetchInput();
        String[] str = (String[]) lines.get(obj);
        assertArrayEquals(new String[] {"anna"}, str);


    }

    @Test
    void parseFilter() throws NoSuchFieldException {
        StoreNameFilter s = new StoreNameFilter("Safeway");
        GroceryNameFilter g = new GroceryNameFilter("cabbage");
        LocalDate ld = LocalDate.parse("2012-12-12");
        LocalDate ld1 = LocalDate.parse("2020-12-12");
        ReceiptDateFilter r = new ReceiptDateFilter(ld,ld1);
        FilterSet fs = new FilterSet(s,g,r);

        CommandLine obj = new CommandLine();
        Field lines = CommandLine.class.getDeclaredField("lines");
        lines.setAccessible(true);
        setInput("avg-price, store name Safeway, grocery name cabbage, receipt between 2012-12-12 2020-12-12\n");
        obj.fetchInput();

        assertEquals(fs.toString(), obj.parseFilter().toString());
    }

    @Test
    void execute() throws NoSuchFieldException {
        CommandLine obj = new CommandLine();
        Field lines = CommandLine.class.getDeclaredField("lines");
        lines.setAccessible(true);

        setInput("anna\n");

        obj.fetchInput();

        StoreNameFilter s = new StoreNameFilter("Safeway");
        GroceryNameFilter g = new GroceryNameFilter("cabbage");
        LocalDate ld = LocalDate.parse("2012-12-12");
        LocalDate ld1 = LocalDate.parse("2020-12-12");
        ReceiptDateFilter r = new ReceiptDateFilter(ld,ld1);
        FilterSet fs = new FilterSet(s,g,r);

        assertThrows(CommandSyntaxException.class, ()->{
            obj.execute(fs);
        });

        setInput("avg-price, store name Safeway, grocery name cabbage, receipt between 2012-12-12 2020-12-12\n");
        obj.fetchInput();
        assertTrue(obj.execute(fs));

        setInput("exit");
        obj.fetchInput();
        assertFalse(obj.execute(fs));
    }
}