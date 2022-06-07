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

/**
 * Factory that crates a grocery object using a UPC or JSON by downloading the information from online
 */
public class UPCGroceryFactory implements GroceryFactory {

    /**
     * overrides the method from the GroceryFactory interface
     * prompts the user for the name of the grocery
     * and looks for the grocery information
     * @return returns the grocery object, or null if an error occurred
     */
    @Override
    public Grocery createGrocery() {
        //Get the name
        String name = promptName();

        //Prompt and parse the nutrition...
        Nutrition n;
        do {
            n = promptAndParseUPC();

            //It succeeded! continue
            if(n != null)
                break;

            //Prompt if they'd like to retry
        } while(UIHelpers.promptBoolean("Failed to get information. Try again?", true));

        //If it failed and they decided to not retry, return no object.
        if(n == null){
            return null;
        }

        //Ask the servings... (the online database servings are notably unreliable)
        double servings = UIHelpers.promptDouble("Servings per container: ");

        //Compose the object and return
        return new Grocery(name, n, servings);
    }

    /**
     * prompts the user for the UPC, re-prompting if its invalid
     * @return returns the string containing the valid UPC
     */
    private static String promptCode() {
        //Get the user input...
        String upc;
        upc = UIHelpers.promptString("Enter the product UPC: ");

        //While the code is not valid...
        while(!validateCode(upc)){
            //prompt again!
            upc = UIHelpers.promptString("Invalid UPC, try again: ");
        }
        return upc;
    }

    /**
     * prompts the user for the name of the grocery
     * @return returns the string containing the name of the grocery
     */
    private static String promptName() {
        return UIHelpers.promptString("Grocery name: ");
    }

    /**
     * prompts the user for the code and parses the string.
     * gets the Nutrition information online
     * @return the Nutrition object, or null if there is a server error or parsing error
     */
    private Nutrition promptAndParseUPC() {
        //Prompt...
        String upc = promptCode();
        //Download...
        String json = getOpenFoodFactsJSON(upc);
        if(json == null)
            return null;
        //Parse!
        return parseJSON(json);
    }

    /**
     * Validates the checksum of the given UPC or EAN.
     * @param upc A 12-digit numeric UPC or 13-digit EAN string to checksum for validity
     * @return whether the UPC represents a valid code according to the UPC-12 or EAN-13 standard
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

    /**
     * download the nutrition information of the given UPC from OpenFoodFacts
     * @param upc string containing the UPC
     * @return the server's JSON response
     */
    public static String getOpenFoodFactsJSON(String upc) {
        System.out.print("Downloading...");

        HttpURLConnection con = null;
        //Begin a connection.
        try {
            //Create a url to the openfoodfacts database, with the given product code, requesting JSON format.
            URL url = new URL("https://world.openfoodfacts.org/api/v0/product/" + upc + ".json");

            //Open a synchronous HTTP GET connection
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            //If the server replied in error,
            if(con.getResponseCode() != 200) {
                //Print a message and return.
                System.out.println("Failed to download information, check your internet connection.");
                return null;
            }
            //Get the raw input stream for the server's response
            InputStream is = con.getInputStream();

            //Read it into a string and return.
            return new BufferedReader(new InputStreamReader(is))
                    .lines().collect(Collectors.joining("\n"));

            //In the case of error...
        } catch(Exception e) {
            //Rethrow as an unchecked exception.
            throw new RuntimeException(e);

            //Finally, in case of exception or not...
        } finally {
            //Close the connection, if it's open
            if(con != null)
                con.disconnect();
        }
    }

    /**
     * parses the JSON String passed in and gets the information for the nutrition object
     * creates a nutrition object
     * @param json string containing the JSON
     * @return the created nutrition object, or null if the json does not contain a valid response.
     */
    public static Nutrition parseJSON(String json) {
        System.out.print("Parsing...");
        //Use the JSON library to parse.
        JSONObject j = new JSONObject(json);
        //If the server replied with a status of not found/other error, return nothing.
        if(j.getInt("status") != 1)
            return null;

        //Get the nutriments property, holding the nutrition information
        JSONObject nutriments = j.getJSONObject("product").getJSONObject("nutriments");

        //Get or 0, allowing for some or all of the fields to be defined.
        double calories = nutriments.optDouble("energy-kcal_serving", 0.0);
        double fat = nutriments.optDouble("fat_serving", 0.0);
        double sugar = nutriments.optDouble("sugars_serving", 0.0);
        double fiber = nutriments.optDouble("fiber_serving", 0.0);
        double protein = nutriments.optDouble("proteins_serving", 0.0);
        double sodium = nutriments.optDouble("sodium_serving", 0.0);

        //Finish and compose the new object.
        System.out.println("Done!");
        return new Nutrition(calories, fat, sugar, fiber, protein, sodium);
    }
}
