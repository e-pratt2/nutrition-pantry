import Database.Grocery;
import Factories.DIYFactory;
import Factories.UPCGroceryFactory;
import Filters.AlwaysPassFilter;
import Filters.Filter;

public class NutritionPantry {
    public static void main(String[] args) {
        UPCGroceryFactory upcFact = new UPCGroceryFactory();
        System.out.println(upcFact.createGrocery());

        DIYFactory fact = new DIYFactory();

        Grocery g = fact.createGrocery();
        System.out.println(g.toString());
    }
}
