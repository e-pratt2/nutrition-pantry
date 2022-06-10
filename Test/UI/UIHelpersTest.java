package UI;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UIHelpersTest {

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
        } catch(Exception e) {
            throw new RuntimeException("Couldn't set inputs for UIHelpersTest");
        }
    }
    //Create a way to read console output
    ByteArrayOutputStream getOutput() {
        try {
            ByteArrayOutputStream outputBuffer = new ByteArrayOutputStream();
            PrintStream printStream = new PrintStream(outputBuffer, true, StandardCharsets.UTF_8.name());

            System.setOut(printStream);

            return outputBuffer;
        } catch(UnsupportedEncodingException e) {
            throw new RuntimeException("Bad encoding in output text");
        }
    }

    @Test
    void promptDouble() {
        InputStream in = System.in;
        PrintStream out = System.out;

        //Create an output stream to ensure printed text is correct

        ByteArrayOutputStream outputBuffer = getOutput();

        setInput("12.34\n");

        //Test with a valid double
        double value = UIHelpers.promptDouble("Double, if you please");
        assertEquals(outputBuffer.toString(), "Double, if you please");
        assertEquals(12.34, value);

        //Reset user input
        setInput("bad input\n33.33\n");

        //Test with an invalid double, followed by a retry
        value = UIHelpers.promptDouble("Another, if you please");
        assertEquals(outputBuffer.toString(), "Double, if you pleaseAnother, if you pleaseInvalid value, try again: ");
        assertEquals(33.33, value);

        //Reset in and out
        System.setIn(in);
        System.setOut(out);
    }

    @Test
    void promptString() {
        InputStream in = System.in;
        PrintStream out = System.out;

        ByteArrayOutputStream outputBuffer = getOutput();

        setInput("zoop f4\n");

        //Test with a valid double
        String value = UIHelpers.promptString("String time");
        assertEquals(outputBuffer.toString(), "String time");
        assertEquals("zoop f4", value);

        //Reset user input
        setInput("weird string \uD83D\uDC11\n");

        //Test with an strange string
        value = UIHelpers.promptString("Another, if you please");
        assertEquals(outputBuffer.toString(), "String timeAnother, if you please");
        assertEquals("weird string \uD83D\uDC11", value);

        //Reset in and out
        System.setIn(in);
        System.setOut(out);
    }

    @Test
    void promptDate() {
        InputStream in = System.in;
        PrintStream out = System.out;

        ByteArrayOutputStream outputBuffer = getOutput();

        setInput("2001-12-30\n");

        //Test with a valid date
        LocalDate value = UIHelpers.promptDate("Cool date");
        assertEquals(outputBuffer.toString(), "Cool date (yyyy-mm-dd) ");
        assertEquals(LocalDate.of(2001, 12, 30), value);

        //Reset user input
        setInput("12-27-2021\nweird stuff\n2022-05-24\n");

        //Test with two bad dates, and a good one
        value = UIHelpers.promptDate("Another, if you please");
        assertEquals(outputBuffer.toString(), "Cool date (yyyy-mm-dd) Another, if you please (yyyy-mm-dd) Invalid date, try again: Invalid date, try again: ");
        assertEquals(LocalDate.of(2022, 05, 24), value);

        //Reset in and out
        System.setIn(in);
        System.setOut(out);
    }

    @Test
    void promptBoolean() {
        InputStream in = System.in;
        PrintStream out = System.out;

        ByteArrayOutputStream outputBuffer = getOutput();

        setInput("y\n");

        //Test with a valid date
        boolean value = UIHelpers.promptBoolean("yes?", true);
        assertEquals(outputBuffer.toString(), "yes? " + ConsoleStyle.bold("[Y/n] ").blue());
        assertTrue(value);

        //Reset user input, a bad bool followed by blank input (take default value)
        setInput("this isn't an answer!\n\n");

        value = UIHelpers.promptBoolean("Another, if you please", false);
        assertEquals(outputBuffer.toString(), "yes? " + ConsoleStyle.bold("[Y/n] ").blue()
                + "Another, if you please " + ConsoleStyle.bold("[y/N] ").blue()
                + "Unrecognized value, try again: ");
        assertFalse(value);

        //Reset in and out
        System.setIn(in);
        System.setOut(out);
    }

    @Test
    void chooseObject() {
        InputStream in = System.in;

        List<String> options = Arrays.asList(
                "Option 1",
                "Option 2",
                "Weird option",
                "Cool option"
        );


        setInput("2\n");

        //Test with a valid date
        String value = UIHelpers.chooseObject(options,(s) -> s);
        assertEquals(options.get(1), value);

        //Reset user input, two bad inputs followed by a good input
        setInput("6\n0\n1\n");

        value = UIHelpers.chooseObject(options,(s) -> s);
        assertEquals(options.get(0), value);

        //Reset in
        System.setIn(in);
    }

    @Test
    void chooseObjectOrOther() {
        InputStream in = System.in;

        List<String> options = Arrays.asList(
                "Option 1",
                "Option 2",
                "Weird option",
                "Cool option"
        );

        String otherOption = "Rad other option";

        setInput("2\n");

        //Test with a valid date
        String value = UIHelpers.chooseObjectOrOther(options,(s) -> s, otherOption);
        assertEquals(options.get(1), value);

        //Reset user input, two bad inputs followed by an "other" input
        setInput("6\n0\n5\n");

        value = UIHelpers.chooseObjectOrOther(options,(s) -> s, otherOption);
        assertEquals(null, value);

        //Reset in
        System.setIn(in);
    }

    @Test
    void promptMenu() {
        InputStream in = System.in;

        String[] options = {
                "Option 1",
                "Option 2",
                "Weird option",
                "Cool option"
        };

        setInput("2\n");

        //Test with a valid date
        int value = UIHelpers.promptMenu(options);
        assertEquals(2, value);

        //Reset user input, two bad inputs followed by an "other" input
        setInput("6\n0\n3\n");

        value = UIHelpers.promptMenu(options);
        assertEquals(3, value);

        //Reset in
        System.setIn(in);
    }

    @Test
    void promptFilepath() {
        InputStream in = System.in;

        setInput("./database.bin\n");

        //Test with a valid date
        Path value = UIHelpers.promptFilepath("Path");
        assertEquals(Path.of("./database.bin"), value);

        //Reset in
        System.setIn(in);
    }
}