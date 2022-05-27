package UI;

import java.util.Arrays;

public class ConsoleStyle {
    public static ConsoleStyleBuilder red(String s) {
        return new ConsoleStyleBuilder(s).red();
    }
    public static ConsoleStyleBuilder grey(String s) {
        return new ConsoleStyleBuilder(s).grey();
    }
    public static ConsoleStyleBuilder green(String s) {
        return new ConsoleStyleBuilder(s).green();
    }
    public static ConsoleStyleBuilder yellow(String s) {
        return new ConsoleStyleBuilder(s).yellow();
    }
    public static ConsoleStyleBuilder blue(String s) {
        return new ConsoleStyleBuilder(s).blue();
    }
    public static ConsoleStyleBuilder magenta(String s) {
        return new ConsoleStyleBuilder(s).magenta();
    }
    public static ConsoleStyleBuilder cyan(String s) {
        return new ConsoleStyleBuilder(s).cyan();
    }
    public static ConsoleStyleBuilder white(String s) {
        return new ConsoleStyleBuilder(s).white();
    }
    public static ConsoleStyleBuilder underline(String s) {
        return new ConsoleStyleBuilder(s).underline();
    }
    public static ConsoleStyleBuilder bold(String s) {
        return new ConsoleStyleBuilder(s).bold();
    }
    public static ConsoleStyleBuilder reverse(String s) {
        return new ConsoleStyleBuilder(s).reverse();
    }
}
