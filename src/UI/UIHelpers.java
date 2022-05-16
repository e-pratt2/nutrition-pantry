package UI;

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

        Scanner kb = new Scanner(System.in);
        String str = kb.nextLine();
        int choice = 0;
        try {
            choice = Integer.parseInt(str);
        }catch(NumberFormatException e){
            System.out.println("invalid value, try again:");
        }

       if(choice > 0 && choice < 8){
           System.out.println(star[choice]);
       }
       return choice;
    }
}
