package Factories;

import Database.Grocery;
import Database.Nutrition;
import UI.UIHelpers;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Collectors;

public class UPCGroceryFactory implements GroceryFactory {
    @Override
    public Grocery createGrocery() {
        String name = promptName();
        Nutrition n = promptAndParseUPC();

        if(n == null){
            return null;
        }
        System.out.println("Done!");

        return new Grocery(name, n);
    }
    private static String promptCode() {
        String upc;
        upc = UIHelpers.promptString("Enter the product UPC:");
        while(!validateCode(upc)){
            upc = UIHelpers.promptString("Invalid UPC, try again:");
        };
        return upc;
    }
    private static String promptName() {
        return UIHelpers.promptString("Grocery name:");
    }
    private Nutrition promptAndParseUPC() {
        String upc = promptCode();
        String json = getOpenFoodFactsJSON(upc);
        if(json == null)
            return null;
        return parseJSON(json);
    }

    /**
     * @param upc A 12-digit numeric UPC string to checksum for validity
     * @return whether the UPC represents a valid code according to the UPC-12 standard
     */
    public static boolean validateCode(String upc) {
        //UPC-12 must be 12 digits, EAN must be 13.
        if(upc.length() < 12 || upc.length() > 13) {
            return false;
        }

        boolean isUPC = upc.length() == 12;

        //Sum up the digits...
        int digitSum = 0;
        for(int i = 0; i < (upc.length()-1); ++i) {
            //Get the digit at the current index
            char c = upc.charAt(i);
            int digit = Character.digit(c, 10);
            //Not a digit - invalid code
            if(digit == -1)
                return false;
            if(isUPC) {
                //Even numbered indices get multiplied by 3
                if ((i & 1) == 0)
                    digit *= 3;
            } else {
                //Odd numbered indices get multiplied by 3
                if ((i & 1) == 1)
                    digit *= 3;
            }
            //Add to our sum
            digitSum += digit;
        }

        //The value of the check digit should be whatever makes the total
        //sum a multiple of 10
        int desiredCheckDigit = 10 - digitSum % 10;
        //Return if the actual check digit matches our theoretical checksum
        char checkChar = upc.charAt(upc.length() - 1);
        return Character.digit(checkChar, 10) == desiredCheckDigit;
    }
    public static String getOpenFoodFactsJSON(String upc) {
        System.out.print("Downloading...");
        HttpURLConnection con = null;
        try {
            URL url = new URL("https://world.openfoodfacts.org/api/v0/product/" + upc + ".json");
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            if(con.getResponseCode() != 200) {
                System.out.println("Failed to download information, check your internet connection.");
                return null;
            }
            InputStream is = con.getInputStream();

            return new BufferedReader(new InputStreamReader(is))
                    .lines().collect(Collectors.joining("\n"));
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if(con != null)
                con.disconnect();
        }
        return null;
    }
    public static Nutrition parseJSON(String json) {
        System.out.print("Parsing...");
        JSONObject j = new JSONObject(json);
        if(j.getInt("status") != 1)
            return null;

        JSONObject nutriments = j.getJSONObject("product").getJSONObject("nutriments");

        double calories = nutriments.optDouble("energy-kcal_serving", 0.0);
        double fat = nutriments.optDouble("fat_serving", 0.0);
        double sugar = nutriments.optDouble("sugars_serving", 0.0);
        double fiber = nutriments.optDouble("fiber_serving", 0.0);
        double protein = nutriments.optDouble("proteins_serving", 0.0);
        double sodium = nutriments.optDouble("sodium_serving", 0.0);

        return new Nutrition(calories, fat, sugar, fiber, protein, sodium);
    }
}
