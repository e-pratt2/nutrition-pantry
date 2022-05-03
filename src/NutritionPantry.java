import Factories.UPCGroceryFactory;

public class NutritionPantry {
    public static void main(String[] args) {
        System.out.println(UPCGroceryFactory.validateUPC("016000264793"));


        String json = UPCGroceryFactory.getOpenFoodFactsJSON("016000264793");
        System.out.println(UPCGroceryFactory.parseJSON(json));
    }
}
