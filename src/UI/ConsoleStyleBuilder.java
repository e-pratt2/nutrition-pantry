package UI;

/**
 * A class to build ANSI strings for styling text in the console, allowing stacking of multiple styles.
 */
public class ConsoleStyleBuilder {
    private String text;
    private StringBuilder builder;

    /**
     * Create a new builder around the specified text.
     * @param s the text to style
     */
    public ConsoleStyleBuilder(String s) {
        this.text = s;
        this.builder = new StringBuilder();
    }

    private static String RED = "\u001b[31;1m",
            RESET = "\u001b[0m",
            GREY = "\u001b[30;1m",
            GREEN = "\u001b[32;1m",
            YELLOW = "\u001b[33;1m",
            BLUE = "\u001b[34;1m",
            MAGENTA = "\u001b[35;1m",
            CYAN = "\u001b[36;1m",
            WHITE = "\u001b[37;1m",
            BOLD = "\u001b[1m",
            UNDERLINE = "\u001b[4m",
            REVERSE = "\u001b[7m";


    /**
     * Helper method - append the given style to the builder's internal buffer.
     * @param style the ANSI-coded string to append to the style
     * @return self
     */
    private ConsoleStyleBuilder build(String style) {
        builder.append(style);
        return this;
    }

    /**
     * Make the text red.
     * @return self, for method call chaining
     */
    public ConsoleStyleBuilder red() {
        return build(RED);
    }
    /**
     * Make the text grey.
     * @return self, for method call chaining
     */
    public ConsoleStyleBuilder grey() {
        return build(GREY);
    }
    /**
     * Make the text green.
     * @return self, for method call chaining
     */
    public ConsoleStyleBuilder green() {
        return build(GREEN);
    }
    /**
     * Make the text yellow.
     * @return self, for method call chaining
     */
    public ConsoleStyleBuilder yellow() {
        return build(YELLOW);
    }
    /**
     * Make the text blue.
     * @return self, for method call chaining
     */
    public ConsoleStyleBuilder blue() {
        return build(BLUE);
    }
    /**
     * Make the text magenta.
     * @return self, for method call chaining
     */
    public ConsoleStyleBuilder magenta() {
        return build(MAGENTA);
    }
    /**
     * Make the text cyan.
     * @return self, for method call chaining
     */
    public ConsoleStyleBuilder cyan() {
        return build(CYAN);
    }
    /**
     * Make the text white.
     * @return self, for method call chaining
     */
    public ConsoleStyleBuilder white() {
        return build(WHITE);
    }
    /**
     * Make the text underlined.
     * @return self, for method call chaining
     */
    public ConsoleStyleBuilder underline() {
        return build(UNDERLINE);
    }
    /**
     * Make the text bold.
     * @return self, for method call chaining
     */
    public ConsoleStyleBuilder bold() {
        return build(BOLD);
    }
    /**
     * Make the text reverse-colored.
     * @return self, for method call chaining
     */
    public ConsoleStyleBuilder reverse() {
        return build(REVERSE);
    }


    /**
     * Convert the styled string into a printable form - consumes the builder.
     * @return the styled, stringified form.
     */
    @Override
    public String toString() {
        return builder.append(text).append(RESET).toString();
    }
}
