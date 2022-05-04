package UI;

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
}
