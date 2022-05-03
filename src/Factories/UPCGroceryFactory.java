package Factories;

import Database.Grocery;
import Database.Nutrition;

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
        char checkChar = upc.charAt(11);
        //Return if the actual check digit matches our theoretical checksum
        return Character.digit(checkChar, 10) == desiredCheckDigit;
    }
    private static String getOpenFoodFactsJSON(String upc) {
        return null;
    }
    private static Nutrition parseJSON(String json) {
        return null;
    }

}
