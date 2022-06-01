package UI;

/**
 * A class to provide static methods for styling console output. Delegates to ConsoleStyleBuilder.java
 */
public class ConsoleStyle {
    /**
     * Create a red string.
     * @param s the string to color.
     * @return a ConsoleStyleBuilder to provide further styling.
     */
    public static ConsoleStyleBuilder red(String s) {
        return new ConsoleStyleBuilder(s).red();
    }
    /**
     * Create a grey string.
     * @param s the string to color.
     * @return a ConsoleStyleBuilder to provide further styling.
     */
    public static ConsoleStyleBuilder grey(String s) {
        return new ConsoleStyleBuilder(s).grey();
    }
    /**
     * Create a green string.
     * @param s the string to color.
     * @return a ConsoleStyleBuilder to provide further styling.
     */
    public static ConsoleStyleBuilder green(String s) {
        return new ConsoleStyleBuilder(s).green();
    }
    /**
     * Create a yellow string.
     * @param s the string to color.
     * @return a ConsoleStyleBuilder to provide further styling.
     */
    public static ConsoleStyleBuilder yellow(String s) {
        return new ConsoleStyleBuilder(s).yellow();
    }
    /**
     * Create a blue string.
     * @param s the string to color.
     * @return a ConsoleStyleBuilder to provide further styling.
     */
    public static ConsoleStyleBuilder blue(String s) {
        return new ConsoleStyleBuilder(s).blue();
    }
    /**
     * Create a magenta string.
     * @param s the string to color.
     * @return a ConsoleStyleBuilder to provide further styling.
     */
    public static ConsoleStyleBuilder magenta(String s) {
        return new ConsoleStyleBuilder(s).magenta();
    }
    /**
     * Create a cyan string.
     * @param s the string to color.
     * @return a ConsoleStyleBuilder to provide further styling.
     */
    public static ConsoleStyleBuilder cyan(String s) {
        return new ConsoleStyleBuilder(s).cyan();
    }
    /**
     * Create a white string.
     * @param s the string to color.
     * @return a ConsoleStyleBuilder to provide further styling.
     */
    public static ConsoleStyleBuilder white(String s) {
        return new ConsoleStyleBuilder(s).white();
    }
    /**
     * Create a underlined string.
     * @param s the string to color.
     * @return a ConsoleStyleBuilder to provide further styling.
     */
    public static ConsoleStyleBuilder underline(String s) {
        return new ConsoleStyleBuilder(s).underline();
    }
    /**
     * Create a bold string.
     * @param s the string to color.
     * @return a ConsoleStyleBuilder to provide further styling.
     */
    public static ConsoleStyleBuilder bold(String s) {
        return new ConsoleStyleBuilder(s).bold();
    }
    /**
     * Create a reversed-color string.
     * @param s the string to color.
     * @return a ConsoleStyleBuilder to provide further styling.
     */
    public static ConsoleStyleBuilder reverse(String s) {
        return new ConsoleStyleBuilder(s).reverse();
    }
}
