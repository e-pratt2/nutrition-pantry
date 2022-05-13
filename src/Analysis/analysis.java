package Analysis;

import Database.Grocery;
import Database.Nutrition;
import Database.Receipt;
import Database.Store;
import Filters.Filter;

public class analysis {

    public double AvgPrice(Filter<Store> filter, Filter<Receipt> filter1, Filter<Grocery> filter2){
        return getAveragePrice(filter, filter1, filter2);
    }

    public Nutrition AvgNutrition(Filter<Store> filter, Filter<Receipt> filter1, Filter<Grocery> filter2){
        return null;
    }

    public double TotalPrice(Filter<Store> filter, Filter<Receipt> filter1, Filter<Grocery> filter2){
        return getTotalPrice(filter, filter1, filter2);
    }

    public Nutrition totalNutrition(Filter<Store> filter, Filter<Receipt> filter1, Filter<Grocery> filter2){
        return getTotalNutrition(filter, filter1, filter2);
    }
}
