package UI;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class UIHelpers {
    public static double promptDouble(String prompt) {
        Scanner s = new Scanner(System.in);
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
        Scanner kb = new Scanner(System.in);
        String str = kb.nextLine();
        return str;
    }
    public static LocalDate promptDate(String prompt) {
        Scanner s = new Scanner(System.in);
        System.out.println(prompt + " (yyyy mm dd)");
        while(true) {
            String str = s.nextLine();
            try {
                return LocalDate.parse(str, DateTimeFormatter.ISO_LOCAL_DATE);
            } catch(DateTimeParseException e) {
                System.out.println("Invalid date, try again.");
            }
        }
    }

    public static int menu(String [] star){

        for(int i = 0; i < star.length; i++){
            System.out.println(star[i]);
        }

        Scanner kb = new Scanner(System.in);

        System.out.println("Please enter your choice:");
        int choice = 0;

        while(true) {
            String str = kb.nextLine();
            try {
                choice = Integer.parseInt(str);

            } catch (NumberFormatException e) {
                System.out.println("invalid value, try again:");
            }
            if (choice <= 0 && choice >= 8) {
                System.out.println("Invalid value, try again");
            }
            else{
                break;}

        }
       return choice;
    }
    public static Path promptFilepath(String prompt) {
        Scanner s = new Scanner(System.in);
        System.out.println(prompt + " (yyyy mm dd)");
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
