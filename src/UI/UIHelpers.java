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

public class UIHelpers {
    private static Scanner kb;

    public static Scanner getScanner() {
        if(kb == null)
            return kb = new Scanner(System.in);
        return kb;
    }

    public static double promptDouble(String prompt) {
        Scanner s = getScanner();
        System.out.println(prompt);
        while(true) {
            try {
                String line = s.nextLine();
                return Double.parseDouble(line);
            } catch (NumberFormatException e) {
                System.out.println("Invalid value, try again.");
            }
        }
    }
    public static String promptString(String prompt) {
        System.out.println(prompt);
        Scanner kb = getScanner();
        String str = kb.nextLine();
        return str;
    }
    public static LocalDate promptDate(String prompt) {
        Scanner s = getScanner();
        System.out.println(prompt + " (yyyy-mm-dd)");
        while(true) {
            String str = s.nextLine();
            try {
                return LocalDate.parse(str, DateTimeFormatter.ISO_LOCAL_DATE);
            } catch(DateTimeParseException e) {
                System.out.println("Invalid date, try again.");
            }
        }
    }
    public static boolean promptBoolean(String prompt, boolean auto) {
        String suffix = auto ? "[Y/n]" : "[y/N]";
        System.out.println(prompt + " " + ConsoleStyle.bold(suffix).blue());
        Scanner kb = getScanner();
        while(true) {
            String str = kb.nextLine();
            if(str.isEmpty())
                return auto;

            if(str.equalsIgnoreCase("n")) return false;
            else if(str.equalsIgnoreCase("y")) return true;
            else System.out.println("Unrecognized value, try again.");
        }
    }
    public static <E> E chooseObject(List<E> objects, Function<E, String> stringify){
        if(objects.isEmpty()) {
            return null;
        }
        String[] strings = new String[objects.size()];
        for(int i = 0; i < strings.length; ++i)
            strings[i] = stringify.apply(objects.get(i));

        return objects.get(promptMenu(strings) - 1);
    }
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

    public static int promptMenu(String [] star){
        int choice = 0;

        for(int i = 0; i < star.length; i++){
            System.out.println(" ] " + ConsoleStyle.bold((i + 1) + ". ").red() + star[i]);
        }

        Scanner kb = getScanner();
        while(true) {
            System.out.println("Please enter your choice:");

            String str = kb.nextLine();
            try {
                choice = Integer.parseInt(str);

                if (choice <= 0 || choice > star.length) {
                    System.out.println("Invalid value, try again");
                    continue;
                } else{
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("invalid value, try again:");
            }

        }
       return choice;
    }
    public static Path promptFilepath(String prompt) {
        Scanner s = getScanner();
        System.out.println(prompt);
        while(true) {
            String str = s.nextLine();
            try {
                return Paths.get(str);
            } catch(InvalidPathException e) {
                System.out.println("Invalid path, try again.");
            }
        }
    }
}
