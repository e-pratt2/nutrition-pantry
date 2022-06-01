package UI;

/**
 * A simple RuntimeException representing an error with the user's input within CommandLine.java
 */
public class CommandSyntaxException extends RuntimeException {
    public CommandSyntaxException(String what) {
        super(what);
    }
}
