import java.util.Scanner;

public class UIHelpers {
    public static double promptDouble(String prompt) {
        Scanner s = new Scanner(System.in);
        while(true) {
            try {
                String line = s.nextLine();
                return Double.parseDouble(line);
            } catch (NumberFormatException e) {
                System.out.println("Invalid value, try again.");
            }
        }
    }
    public String promptString(String prompt) {

    }
}
