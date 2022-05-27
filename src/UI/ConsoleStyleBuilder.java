package UI;

public class ConsoleStyleBuilder {
    private String text;
    private StringBuilder builder;

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
    private ConsoleStyleBuilder build(String style) {
        builder.append(style);

        return this;
    }
    public ConsoleStyleBuilder red() {
        return build(RED);
    }
    public ConsoleStyleBuilder grey() {
        return build(GREY);
    }
    public ConsoleStyleBuilder green() {
        return build(GREEN);
    }
    public ConsoleStyleBuilder yellow() {
        return build(YELLOW);
    }
    public ConsoleStyleBuilder blue() {
        return build(BLUE);
    }
    public ConsoleStyleBuilder magenta() {
        return build(MAGENTA);
    }
    public ConsoleStyleBuilder cyan() {
        return build(CYAN);
    }
    public ConsoleStyleBuilder white() {
        return build(WHITE);
    }
    public ConsoleStyleBuilder underline() {
        return build(UNDERLINE);
    }
    public ConsoleStyleBuilder bold() {
        return build(BOLD);
    }
    public ConsoleStyleBuilder reverse() {
        return build(REVERSE);
    }

    @Override
    public String toString() {
        return builder.append(text).append(RESET).toString();
    }
}
