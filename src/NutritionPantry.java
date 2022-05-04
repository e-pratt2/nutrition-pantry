import Factories.UPCGroceryFactory;

public class NutritionPantry {
    public static void main(String[] args) {
        System.out.println(UPCGroceryFactory.validateUPC("5449000000996"));


        String json = UPCGroceryFactory.getOpenFoodFactsJSON("016000264793");
        System.out.println("\n\n\n" + json + "\n\n\n\n");
        System.out.println(UPCGroceryFactory.parseJSON(json));
    }
}
