import Database.Grocery;
import Database.SerializableDatabase;
import Factories.DIYFactory;
import Factories.UPCGroceryFactory;
import Filters.AlwaysPassFilter;
import Filters.Filter;
import UI.UIHelpers;

public class NutritionPantry {
    public static final String menuOptions[] = {
            "1. Add store...",
            "Add receipts to store...",
            "Add groceries...",
            "Analysis...",
            "Load from file...",
            "Save to file...",
            "Save and exit."
    };
    public static void main(String[] args) {
        switch(UIHelpers::promptMenu(menuOptions)) {
            case 1: break;
            case 2: break;
            case 3: break;
            case 4: break;
            case 5: break;
            case 6: break;
            case 7:
                SerializableDatabase.saveInstance(UIHelpers.promptFilepath("Save path:"));
                return;

        }
    }
}
