package UI;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class UIHelpersTest {

    //Create a way to emulate user input
    void setInput(String s) {
        InputStream testInput = new ByteArrayInputStream(s.getBytes(StandardCharsets.UTF_8));
        System.setIn(testInput);
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
        assertEquals(outputBuffer.toString(), "Double, if you please\r\n");
        assertEquals(12.34, value);

        //Reset user input
        setInput("bad input\n33.33\n");

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
        InputStream in = System.in;
        PrintStream out = System.out;

        ByteArrayOutputStream outputBuffer = getOutput();

        setInput("zoop f4\n");

        //Test with a valid double
        String value = UIHelpers.promptString("String time");
        assertEquals(outputBuffer.toString(), "String time\r\n");
        assertEquals("zoop f4", value);

        //Reset user input
        setInput("weird string \uD83D\uDC11\n");

        //Test with an invalid double, followed by a retry
        value = UIHelpers.promptString("Another, if you please");
        assertEquals(outputBuffer.toString(), "String time\r\nAnother, if you please\r\n");
        assertEquals("weird string \uD83D\uDC11", value);

        //Reset in and out
        System.setIn(in);
        System.setOut(out);
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