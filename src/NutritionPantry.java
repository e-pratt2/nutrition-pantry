import Database.Grocery;
import Factories.DIYFactory;
import Factories.UPCGroceryFactory;

public class NutritionPantry {
    public static void main(String[] args) {
        System.out.println(UPCGroceryFactory.validateCode("5449000000996"));

        System.out.println();

        String json = UPCGroceryFactory.getOpenFoodFactsJSON("016000264793");
        System.out.println("\n\n\n" + json + "\n\n\n\n");
        System.out.println(UPCGroceryFactory.parseJSON(json));

        DIYFactory fact = new DIYFactory();

        Grocery g = fact.createGrocery();
        System.out.println(g.toString());
    }
}
