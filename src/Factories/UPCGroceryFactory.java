package Factories;

import Database.Grocery;
import Database.Nutrition;
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
        return null;
    }
    private static String promptUPC() {
        return null;
    }

    /**
     * @param upc A 12-digit numeric UPC string to checksum for validity
     * @return whether the UPC represents a valid code according to the UPC-12 standard
     */
    public static boolean validateUPC(String upc) {
        //UPC-12 must be 12 digits
        if(upc.length() != 12) {
            return false;
        }

        //Sum up the digits...
        int digitSum = 0;
        for(int i = 0; i < 11; ++i) {
            //Get the digit at the current index
            char c = upc.charAt(i);
            int digit = Character.digit(c, 10);
            //Not a digit - invalid code
            if(digit == -1)
                return false;
            //Even numbered indices get multiplied by 3
            if((i & 1) == 0)
                digit *= 3;
            //Add to our sum
            digitSum += digit;
        }

        //The value of the check digit should be whatever makes the total
        //sum a multiple of 10
        int desiredCheckDigit = 10 - digitSum % 10;
        //Return if the actual check digit matches our theoretical checksum
        char checkChar = upc.charAt(11);
        return Character.digit(checkChar, 10) == desiredCheckDigit;
    }
    public static String getOpenFoodFactsJSON(String upc) {
        HttpURLConnection con = null;
        try {
            URL url = new URL("https://world.openfoodfacts.org/api/v0/product/" + upc + ".json");
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            System.out.println(con.getResponseCode());
            System.out.println(con.getResponseMessage());

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
        JSONObject j = new JSONObject(json);
        if(j.getInt("status") != 1)
            return null;

        JSONObject nutriments = j.getJSONObject("product").getJSONObject("nutriments");

        double calories = nutriments.optDouble("energy_serving", 0.0);
        double fat = nutriments.optDouble("fat_serving", 0.0);
        double sugar = nutriments.optDouble("sugars_serving", 0.0);
        double fiber = nutriments.optDouble("fiber_serving", 0.0);
        double protein = nutriments.optDouble("proteins_serving", 0.0);
        double sodium = nutriments.optDouble("sodium_serving", 0.0);

        return new Nutrition(calories, fat, sugar, fiber, protein, sodium);
    }
}
