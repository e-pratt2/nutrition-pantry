package UI;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

/**
 * A set of common helper methods for prompting for user input of different formats and types.
 * Used throughout the codebase.
 * Also provides singleton access to a global Scanner object reading System.in
 */
public class UIHelpers {
    private static Scanner kb;

    /**
     * Get (or create) a global scanner singleton referencing System.in
     * Reduces conflicts between multiple scanners and hanging newlines.
     * @return Scanner - a global reader of System.in
     */
    public static Scanner getScanner() {
        if(kb == null)
            return kb = new Scanner(System.in);
        return kb;
    }


    /**
     * Prompts the user for a double value with the given prompt. Will repeatedly retry until a valid double
     * is input, so the return value will always be a valid double (may be NaN/inf however)
     * @param prompt A string to show the user to give context for what value they are inputting.
     * @return double - the parsed value inputted by the user.
     */
    public static double promptDouble(String prompt) {
        Scanner s = getScanner();
        System.out.print(prompt);
        while(true) {
            try {
                String line = s.nextLine();
                return Double.parseDouble(line);
            } catch (NumberFormatException e) {
                System.out.print("Invalid value, try again: ");
            }
        }
    }

    /**
     * Prompts the user for an unformatted string, allowing empty strings and whitespace strings.
     * @param prompt The text to display to the user to ask for input.
     * @return String - the text the user has input. May be empty or whitespace, but not null
     */
    public static String promptString(String prompt) {
        System.out.print(prompt);
        Scanner kb = getScanner();
        String str = kb.nextLine();
        return str;
    }
    /**
     * Prompts the user for an ISO LOCAL formatted string (yyy-mm-dd), retrying until a valid date is read.
     * Automatically appends a format-hint to the provided `prompt`.
     * @param prompt The text to display to the user to ask for input.
     * @return LocalDate - the parsed date. Will always be a valid date.
     */
    public static LocalDate promptDate(String prompt) {
        Scanner s = getScanner();
        System.out.print(prompt + " (yyyy-mm-dd) ");
        while(true) {
            String str = s.nextLine();
            try {
                return LocalDate.parse(str, DateTimeFormatter.ISO_LOCAL_DATE);
            } catch(DateTimeParseException e) {
                System.out.print("Invalid date, try again: ");
            }
        }
    }

    /**
     * Prompts the user with a true/false yes/no question. Allows for an auto parameter, which specifies the default
     * value if the user simply hits enter without any input (a nice convenience for going through many menus)
     * Appends a format hint in the form `[y/N]` or `[Y/n]` with the capital letter representing the auto value.
     * @param prompt The text to display to the user to ask for input.
     * @param auto The "auto"/default value, should the user press enter with no input.
     * @return boolean - the parsed value of the input. Will always be valid.
     */
    public static boolean promptBoolean(String prompt, boolean auto) {
        String suffix = auto ? "[Y/n] " : "[y/N] ";
        System.out.print(prompt + " " + ConsoleStyle.bold(suffix).blue());
        Scanner kb = getScanner();
        while(true) {
            String str = kb.nextLine();
            if(str.isEmpty())
                return auto;

            if(str.equalsIgnoreCase("n")) return false;
            else if(str.equalsIgnoreCase("y")) return true;
            else System.out.print("Unrecognized value, try again: ");
        }
    }

    /**
     * Allow the user to choose from a set of objects, returning the object. Prints them out according to the
     * stringify function, which the user then selects by index.
     * @param objects The list of objects to allow the user to choose from.
     * @param stringify The function that converts each object to a string for printing. Can be bound to E::toString
     * @param <E> The generic type of the objects contained in the list.
     * @return E - The object selected by the user. `null` if the list was empty, or if a null object was chosen from the list.
     */
    public static <E> E chooseObject(List<E> objects, Function<E, String> stringify){
        if(objects.isEmpty()) {
            return null;
        }
        String[] strings = new String[objects.size()];
        for(int i = 0; i < strings.length; ++i)
            strings[i] = stringify.apply(objects.get(i));

        return objects.get(promptMenu(strings) - 1);
    }

    /**
     * Allow the user to choose from a set of objects, returning the object. Prints them out according to the
     * stringify function, which the user then selects by index. Provides an extra option specified by `otherOption`
     * that the user can choose as well, which returns `null` to indicate this choice. Helpful for "choose-or-add" type menus.
     * @param objects The list of objects to allow the user to choose from.
     * @param stringify The function that converts each object to a string for printing. Can be bound to E::toString
     * @param otherOption A string to print as an extra option, returns null when chosen to indicate this special choice.
     * @param <E> The generic type of the objects contained in the list.
     * @return E - The object selected by the user. `null` if the list was empty, or if the user selected the `otherOption`
     */
    public static <E> E chooseObjectOrOther(List<E> objects, Function<E, String> stringify, String otherOption){
        if(objects.isEmpty()) {
            return null;
        }
        String[] strings = new String[objects.size() + 1];
        for(int i = 0; i < objects.size(); ++i)
            strings[i] = stringify.apply(objects.get(i));

        strings[objects.size()] = otherOption;

        int chosen = promptMenu(strings) - 1;
        if(chosen == objects.size())
            return null;
        else
            return objects.get(chosen);
    }

    /**
     * @param star An array of strings to print in list form, allowing the user to choose
     * @return int - the 1-based index of the user's choice. Ranges from [1, star.length + 1)
     */
    public static int promptMenu(String [] star){
        int choice = 0;

        for(int i = 0; i < star.length; i++){
            System.out.println(" ] " + ConsoleStyle.bold((i + 1) + ". ").red() + star[i]);
        }

        Scanner kb = getScanner();
        while(true) {
            System.out.print("Please enter your choice: ");

            String str = kb.nextLine();
            try {
                choice = Integer.parseInt(str);

                if (choice <= 0 || choice > star.length) {
                    System.out.print("Invalid value, try again: ");
                    continue;
                } else{
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.print("invalid value, try again: ");
            }

        }
       return choice;
    }
    public static Path promptFilepath(String prompt) {
        Scanner s = getScanner();
        System.out.print(prompt);
        while(true) {
            String str = s.nextLine();
            try {
                return Paths.get(str);
            } catch(InvalidPathException e) {
                System.out.print("Invalid path, try again: ");
            }
        }
    }
}
