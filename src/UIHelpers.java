import java.util.Scanner;

public class UIHelpers {
    public double promptDouble(String prompt) {

    }
    public String promptString(String prompt) {

        Scanner kb = new Scanner(System.in);
        String str = kb.nextLine();
        return str;
    }
}
