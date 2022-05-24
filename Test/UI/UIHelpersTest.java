package UI;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class UIHelpersTest {

    @Test
    void promptDouble() throws UnsupportedEncodingException {
        InputStream in = System.in;
        PrintStream out = System.out;

        //Create an output stream to ensure printed text is correct
        ByteArrayOutputStream outputBuffer = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputBuffer, true, StandardCharsets.UTF_8.name());

        System.setOut(printStream);

        //Create a way to emulate user input
        InputStream testInput = new ByteArrayInputStream("12.34\n".getBytes(StandardCharsets.UTF_8));
        System.setIn(testInput);

        //Test with a valid double
        double value = UIHelpers.promptDouble("Double, if you please");
        assertEquals(outputBuffer.toString(), "Double, if you please\r\n");
        assertEquals(12.34, value);

        //Reset user input
        testInput = new ByteArrayInputStream("bad input\n33.33\n".getBytes(StandardCharsets.UTF_8));
        System.setIn(testInput);

        //Test with an invalid double, followed by a retry
        value = UIHelpers.promptDouble("Another, if you please");
        assertEquals(outputBuffer.toString(), "Double, if you please\r\nAnother, if you please\r\nInvalid value, try again.\r\n");
        assertEquals(33.33, value);

        //Reset in and out
        System.setIn(in);
        System.setOut(out);
    }

    @Test
    void promptString() {
    }

    @Test
    void promptDate() {
    }

    @Test
    void promptBoolean() {
    }

    @Test
    void chooseObject() {
    }

    @Test
    void chooseObjectOrOther() {
    }

    @Test
    void menu() {
    }

    @Test
    void promptFilepath() {
    }
}